import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.CountingIdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.di.module.ApplicationModule;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.ui.activity.RecipeActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.List;

import di.DaggerMockApplicationComponent;
import di.MockApplicationComponent;
import di.MockDataModule;
import fake.RecipeProvider;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeScreenTest {

    private IRepository<Recipe> mockRepository;
    private CountingIdlingResource countingIdlingResource;
    private final int DELAYED_TIMEOUT=1000;

    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule=new ActivityTestRule<>(RecipeActivity.class,true,false);

    @Before
    public void setUp(){
        countingIdlingResource = new CountingIdlingResource("countingIdlingResource");
        Instrumentation instrumentation= InstrumentationRegistry.getInstrumentation();
        BakingApp app=BakingApp.class.cast(instrumentation.getTargetContext().getApplicationContext());
        MockApplicationComponent component= DaggerMockApplicationComponent.builder()
                .mockDataModule(new MockDataModule())
                .applicationModule(new ApplicationModule(app))
                .build();
        mockRepository=component.repository();
        app.setAppComponent(component);
    }

    @Test
    public void loadsRecipesFromNetworkAndShowsAsList() {
        List<Recipe> data = RecipeProvider.provideRecipes();
        when(mockRepository.getRecipes()).thenAnswer(new Answer<Observable<List<Recipe>>>() {
            @Override
            public Observable<List<Recipe>> answer(InvocationOnMock invocationOnMock) throws Throwable {
                countingIdlingResource.increment();
                return Observable.fromCallable(() -> {
                    Thread.sleep(DELAYED_TIMEOUT);
                    countingIdlingResource.decrement();
                    return data;
                });
            }
        });
        activityTestRule.launchActivity(null);
        onView(withId(R.id.recipe_list)).check(matches(isDisplayed()));
        //check out if provided items are visible
        for (int index = 0; index < data.size(); index++) {
            Recipe recipe = data.get(index);
            String steps = Integer.toString(recipe.getSteps().size());
            String servings = Integer.toString(recipe.getServings());

            onView(withId(R.id.recipe_list))
                    .perform(RecyclerViewActions.scrollToPosition(index));
            Matcher<View> visibleSibling = hasSibling(allOf(withId(R.id.recipe_title), withText(recipe.getName())));
            onView(allOf(withId(R.id.recipe_title), withText(recipe.getName())))
                    .check(matches(TestRecipeMatchers.withTextAndDisplayed(recipe.getName())));
            onView(allOf(withId(R.id.step_label), visibleSibling))
                    .check(matches(allOf(TestRecipeMatchers.withColoredText(R.string.steps_label, steps),isDisplayed())));
            onView(allOf(withId(R.id.servings_label), visibleSibling))
                    .check(matches(allOf(TestRecipeMatchers.withColoredText(R.string.servings_label, servings),isDisplayed())));
            if (recipe.getImageUrl() == null) {
                onView(allOf(withId(R.id.recipe_image), visibleSibling))
                        .check(matches(allOf(TestRecipeMatchers.withDrawable(R.drawable.icon_cake), isDisplayed())));
            }
        }
    }


    @Test
    public void showsErrorMessageWhenLoadingRecipesFailed(){
        when(mockRepository.getRecipes()).thenReturn(Observable.error(new Exception()));
        activityTestRule.launchActivity(null);

        onView(withId(R.id.message)).check(matches(allOf(isDisplayed(),
                    TestRecipeMatchers.withCompoundDrawable(R.drawable.ic_empty_box),
                    withText(R.string.message_no_network))));
    }

    @Test
    public void showsErrorMessageWhenGetsEmptyData(){
        when(mockRepository.getRecipes()).thenReturn(Observable.just(null));
        activityTestRule.launchActivity(null);

        onView(withId(R.id.message)).check(matches(allOf(isDisplayed(),
                TestRecipeMatchers.withCompoundDrawable(R.drawable.ic_empty_box),
                withText(R.string.message_empty_query))));
    }

    @After
    public void tearDown(){
        Espresso.unregisterIdlingResources(countingIdlingResource);
    }
}

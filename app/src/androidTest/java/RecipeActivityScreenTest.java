import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.ui.activity.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule=new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void testClickOnRecipe_OpensDetailsActivity(){

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.action_bar)).check(matches(withText("Brownies")));
    }
}

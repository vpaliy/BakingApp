package data.local;


import android.net.Uri;

import com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import com.vpaliy.bakingapp.data.local.RecipeMatchEnum;
import com.vpaliy.bakingapp.data.local.RecipeUriMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


@RunWith(RobolectricTestRunner.class)
public class RecipeUriMatcherTest {

    private RecipeUriMatcher matcher;
    private Map<RecipeMatchEnum, Uri> matchMap;

    @Before
    public void setUp() {
        matcher = new RecipeUriMatcher();
        matchMap = new HashMap<>();
        matchMap.put(RecipeMatchEnum.RECIPES, Recipes.CONTENT_URI);
        matchMap.put(RecipeMatchEnum.RECIPE_ID, Recipes.buildRecipeUri("1"));
        matchMap.put(RecipeMatchEnum.RECIPE_STEP_ID, Recipes.buildRecipeWithStepsUri("2"));
        matchMap.put(RecipeMatchEnum.RECIPE_INGREDIENTS_ID, Recipes.buildRecipeWithIngredientsUri("3"));
        matchMap.put(RecipeMatchEnum.INGREDIENT_ID, Ingredients.buildIngredientUri("1"));
        matchMap.put(RecipeMatchEnum.INGREDIENTS, Ingredients.CONTENT_URI);
        matchMap.put(RecipeMatchEnum.INGREDIENT_RECIPES_ID, Ingredients.buildIngredientWithRecipesUri("1"));
        matchMap.put(RecipeMatchEnum.STEPS, Steps.CONTENT_URI);
        matchMap.put(RecipeMatchEnum.STEP_ID, Steps.buildStepUri("1"));
    }

    @Test
    public void matchesToRecipeMatchEnumOnPassedUri() {
        for (RecipeMatchEnum value : matchMap.keySet()) {
            Uri uri = matchMap.get(value);
            assertThat(value, is(matcher.match(uri)));
        }
    }

    @Test
    public void returnsContentTypeOfEnumOnPassedUri() {
        for (RecipeMatchEnum value : RecipeMatchEnum.values()) {
            Uri uri = matchMap.get(value);
            assertThat(value.contentType, is(matcher.getType(uri)));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void throwsAnExceptionOnPassedUri() {
        Uri fakeUri = Recipes.CONTENT_URI.buildUpon().appendPath("fake_path").build();
        matcher.match(fakeUri);
        matcher.getType(fakeUri);
    }
}

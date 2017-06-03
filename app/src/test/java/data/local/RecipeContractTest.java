package data.local;

import android.net.Uri;

import com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import org.robolectric.RobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class RecipeContractTest {

    @Test
    public void returnsNotNullUri(){
        String id="1";
        assertThat(Recipes.CONTENT_URI,notNullValue());
        assertThat(Recipes.buildRecipeUri(id),notNullValue());
        assertThat(Recipes.buildRecipeWithIngredientsUri(id),notNullValue());
        assertThat(Recipes.buildRecipeWithStepsUri(id),notNullValue());
        assertThat(Steps.CONTENT_URI,notNullValue());
        assertThat(Steps.buildStepUri(id),notNullValue());
        assertThat(Ingredients.CONTENT_URI,notNullValue());
        assertThat(Ingredients.buildIngredientUri(id),notNullValue());
        assertThat(Ingredients.buildIngredientWithRecipesUri(id),notNullValue());
    }

}

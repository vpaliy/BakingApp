package common;

import android.content.ContentValues;

import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import java.util.Arrays;
import java.util.List;
import com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import com.vpaliy.bakingapp.data.local.RecipeContract.Steps;

public class RecipeTestUtils {

    public static final int FAKE_ID=123;
    public static final int FAKER_ID=1234;
    public static final int FAKE_SERVINGS=45;
    public static final double FAKE_QUANTITY=1.5F;
    public static final String FAKE_NAME="fake_name";
    public static final String FAKE_TEXT ="fake_text";
    public static final String FAKE_IMAGE_URL="fake_image_url";
    public static final String FAKE_VIDEO_URL="fake_video_url";
    public static final String FAKE_DESCRIPTION="fake_description";

    public static IngredientEntity provideIngredientEntity(){
        IngredientEntity entity=new IngredientEntity();
        entity.setId(FAKE_ID);
        entity.setQuantity(FAKE_QUANTITY);
        entity.setIngredient(FAKE_NAME);
        entity.setMeasure(FAKE_TEXT);
        return entity;
    }

    public static StepEntity provideStepEntity(){
        StepEntity stepEntity=new StepEntity();
        stepEntity.setImageUrl(FAKE_IMAGE_URL);
        stepEntity.setVideoUrl(FAKE_VIDEO_URL);
        stepEntity.setDescription(FAKE_TEXT);
        stepEntity.setId(FAKE_ID);
        stepEntity.setShortDescription(FAKE_DESCRIPTION);
        return stepEntity;
    }

    public static Ingredient provideIngredient(){
        Ingredient ingredient=new Ingredient();
        ingredient.setId(FAKE_ID);
        ingredient.setQuantity(FAKE_QUANTITY);
        ingredient.setIngredient(FAKE_NAME);
        ingredient.setMeasure(FAKE_TEXT);
        return ingredient;
    }

    public static Step provideStep(){
        Step step=new Step();
        step.setImageUrl(FAKE_IMAGE_URL);
        step.setVideoUrl(FAKE_VIDEO_URL);
        step.setDescription(FAKE_TEXT);
        step.setStepId(FAKE_ID);
        step.setShortDescription(FAKE_DESCRIPTION);
        return step;
    }

    public static List<Step> provideStepList(){
        return Arrays.asList(provideStep(),provideStep(),
                provideStep(),provideStep(),provideStep());
    }

    public static List<StepEntity> provideStepEntityList(){
        return Arrays.asList(provideStepEntity(),provideStepEntity(),
                provideStepEntity(),provideStepEntity(),provideStepEntity());
    }

    public static List<IngredientEntity> provideIngredientEntityList(){
        return Arrays.asList(provideIngredientEntity(), provideIngredientEntity(),
                provideIngredientEntity(), provideIngredientEntity());
    }

    public static List<Ingredient> provideIngredientList(){
        return Arrays.asList(provideIngredient(), provideIngredient(),
                provideIngredient(), provideIngredient());
    }

    public static List<RecipeEntity> provideRecipeEntityList(){
        return Arrays.asList(provideRecipeEntity(),provideRecipeEntity(),
                provideRecipeEntity(),provideRecipeEntity(),provideRecipeEntity());
    }

    public static List<Recipe> provideRecipeList(){
        return Arrays.asList(provideRecipe(),provideRecipe(),provideRecipe(),
                provideRecipe(),provideRecipe(),provideRecipe());
    }

    public static RecipeEntity provideRecipeEntity(){
        RecipeEntity recipeEntity=new RecipeEntity();
        recipeEntity.setIngredients(provideIngredientEntityList());
        recipeEntity.setSteps(provideStepEntityList());
        recipeEntity.setName(FAKE_NAME);
        recipeEntity.setServings(FAKE_SERVINGS);
        recipeEntity.setId(FAKE_ID);
        recipeEntity.setImageUrl(FAKE_IMAGE_URL);
        return recipeEntity;
    }

    public static Recipe provideRecipe(){
        Recipe recipe=new Recipe();
        recipe.setIngredients(provideIngredientList());
        recipe.setSteps(provideStepList());
        recipe.setName(FAKE_NAME);
        recipe.setServings(FAKE_SERVINGS);
        recipe.setId(FAKE_ID);
        recipe.setImageUrl(FAKE_IMAGE_URL);
        return recipe;
    }

}

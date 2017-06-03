package data.mapper;


import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import java.util.Arrays;

class MapperTest {

    static final int FAKE_ID=123;
    static final int FAKE_SERVINGS=45;
    static final double FAKE_QUANTITY=1.5F;
    static final String FAKE_NAME="fake_name";
    static final String FAKE_TEXT ="fake_text";
    static final String FAKE_IMAGE_URL="fake_image_url";
    static final String FAKE_VIDEO_URL="fake_video_url";
    static final String FAKE_DESCRIPTION="fake_description";

    IngredientEntity provideIngredientEntity(){
        IngredientEntity entity=new IngredientEntity();
        entity.setId(FAKE_ID);
        entity.setQuantity(FAKE_QUANTITY);
        entity.setIngredient(FAKE_NAME);
        entity.setMeasure(FAKE_TEXT);
        return entity;
    }

    StepEntity provideStepEntity(){
        StepEntity stepEntity=new StepEntity();
        stepEntity.setImageUrl(FAKE_IMAGE_URL);
        stepEntity.setVideoUrl(FAKE_VIDEO_URL);
        stepEntity.setDescription(FAKE_TEXT);
        stepEntity.setId(FAKE_ID);
        stepEntity.setShortDescription(FAKE_DESCRIPTION);
        return stepEntity;
    }

    Ingredient provideIngredient(){
        Ingredient ingredient=new Ingredient();
        ingredient.setId(FAKE_ID);
        ingredient.setQuantity(FAKE_QUANTITY);
        ingredient.setIngredient(FAKE_NAME);
        ingredient.setMeasure(FAKE_TEXT);
        return ingredient;
    }

    Step provideStep(){
        Step step=new Step();
        step.setImageUrl(FAKE_IMAGE_URL);
        step.setVideoUrl(FAKE_VIDEO_URL);
        step.setDescription(FAKE_TEXT);
        step.setStepId(FAKE_ID);
        step.setShortDescription(FAKE_DESCRIPTION);
        return step;
    }

    RecipeEntity provideRecipeEntity(){
        RecipeEntity recipeEntity=new RecipeEntity();
        recipeEntity.setIngredients(Arrays.asList(provideIngredientEntity(), provideIngredientEntity(),
                provideIngredientEntity(), provideIngredientEntity()));
        recipeEntity.setSteps(Arrays.asList(provideStepEntity(),provideStepEntity(),
                provideStepEntity(),provideStepEntity(),provideStepEntity()));
        recipeEntity.setName(FAKE_NAME);
        recipeEntity.setServings(FAKE_SERVINGS);
        recipeEntity.setId(FAKE_ID);
        recipeEntity.setImageUrl(FAKE_IMAGE_URL);
        return recipeEntity;
    }

    Recipe provideRecipe(){
        Recipe recipe=new Recipe();
        recipe.setIngredients(Arrays.asList(provideIngredient(), provideIngredient(),
                provideIngredient(), provideIngredient()));
        recipe.setSteps(Arrays.asList(provideStep(),provideStep(),
                provideStep(),provideStep(),provideStep()));
        recipe.setName(FAKE_NAME);
        recipe.setServings(FAKE_SERVINGS);
        recipe.setId(FAKE_ID);
        recipe.setImageUrl(FAKE_IMAGE_URL);
        return recipe;
    }

}

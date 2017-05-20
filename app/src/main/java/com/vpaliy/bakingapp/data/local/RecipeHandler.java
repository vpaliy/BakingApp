package com.vpaliy.bakingapp.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import java.util.List;

import static com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import static com.vpaliy.bakingapp.data.local.RecipeDatabase.RecipesIngredients;

class RecipeHandler {

    private ContentResolver contentResolver;

    private RecipeHandler(ContentResolver resolver){
        this.contentResolver=resolver;
    }

    public static RecipeHandler start(ContentResolver resolver){
        return new RecipeHandler(resolver);
    }

    public RecipeHandler insert(RecipeEntity recipeEntity){
        if(recipeEntity!=null){
            ContentValues values=recipeToValues(recipeEntity);
            contentResolver.insert(Recipes.CONTENT_URI,values);
        }
        return this;
    }

    public RecipeHandler insertIngredients(int recipeId, List<IngredientEntity> ingredients){
        if(ingredients!=null) {
            ContentValues values = new ContentValues();
            ingredients.forEach(ingredient -> {
                values.clear();
                values.put(RecipesIngredients.RECIPE_ID, recipeId);
                values.put(RecipesIngredients.INGREDIENT_ID, ingredient.getId());
                Uri contentUri = Recipes.buildRecipeWithIngredienstUri(Integer.toString(recipeId));
                contentResolver.insert(contentUri, values);
                contentUri=Ingredients.buildIngredientWithRecipesUri(Integer.toString(ingredient.getId()));
                contentResolver.insert(contentUri,values);
                values.clear();
                values.put(Ingredients.INGREDIENT_ID,ingredient.getId());
                values.put(Ingredients.INGREDIENT_MEASURE,ingredient.getMeasure());
                values.put(Ingredients.INGREDIENT_NAME,ingredient.getIngredient());
                values.put(Ingredients.INGREDIENT_QUANTITY,ingredient.getQuantity());
                contentResolver.insert(Ingredients.CONTENT_URI,values);
            });
        }
        return this;
    }

    public RecipeHandler insertSteps(int recipeId, List<StepEntity> steps){
        if (steps != null) {
            ContentValues values=new ContentValues();
            steps.forEach(stepEntity -> {
                values.put(Steps.STEP_ID,stepEntity.getId());
                values.put(Steps.STEP_DESCRIPTION,stepEntity.getDescription());
                values.put(Steps.STEP_SHORT_DESCRIPTION,stepEntity.getShortDescription());
                values.put(Steps.STEP_IMAGE_URL,stepEntity.getImageUrl());
                values.put(Steps.STEP_VIDEO_URL,stepEntity.getVideoUrl());
                values.put(Steps.STEP_RECIPE_ID,recipeId);
                contentResolver.insert(Steps.CONTENT_URI,values);
                values.clear();
            });
        }
        return this;
    }


    private ContentValues recipeToValues(RecipeEntity recipe){
        ContentValues values=new ContentValues();
        values.put(Recipes.RECIPE_ID,recipe.getId());
        values.put(Recipes.RECIPE_IMAGE_URL,recipe.getImageUrl());
        values.put(Recipes.RECIPE_NAME,recipe.getName());
        values.put(Recipes.RECIPE_SERVINGS,recipe.getServings());
        return values;
    }

}

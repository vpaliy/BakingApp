package com.vpaliy.bakingapp.data.local;


import android.content.ContentValues;
import android.database.Cursor;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper.RecipesIngredients;

public class DatabaseUtils {

    public static ContentValues toValues(RecipeEntity recipe){
        if(recipe==null) return null;
        ContentValues values=new ContentValues();
        values.put(Recipes.RECIPE_ID,recipe.getId());
        values.put(Recipes.RECIPE_NAME,recipe.getName());
        values.put(Recipes.RECIPE_SERVINGS,recipe.getServings());
        values.put(Recipes.RECIPE_IMAGE_URL,recipe.getImageUrl());
        return values;
    }

    public static ContentValues toValues(IngredientEntity ingredient){
        if(ingredient==null) return null;
        ContentValues values=new ContentValues();
        values.put(Ingredients.INGREDIENT_ID,ingredient.getId());
        values.put(Ingredients.INGREDIENT_NAME,ingredient.getIngredient());
        values.put(Ingredients.INGREDIENT_QUANTITY,ingredient.getQuantity());
        values.put(Ingredients.INGREDIENT_MEASURE,ingredient.getMeasure());
        return values;
    }

    public static ContentValues toValues(IngredientEntity ingredientEntity,
                                         RecipeEntity recipeEntity){
        if(ingredientEntity==null||recipeEntity==null) return null;
        ContentValues values=new ContentValues();
        values.put(RecipesIngredients.RECIPE_ID,recipeEntity.getId());
        values.put(RecipesIngredients.INGREDIENT_ID,ingredientEntity.getId());
        return values;
    }

    public static ContentValues toValues(StepEntity step, int recipeId){
        if(step==null) return null;
        ContentValues values=new ContentValues();
        values.put(Steps.STEP_ID,step.getId());
        values.put(Steps.STEP_DESCRIPTION,step.getDescription());
        values.put(Steps.STEP_SHORT_DESCRIPTION,step.getShortDescription());
        values.put(Steps.STEP_VIDEO_URL,step.getVideoUrl());
        values.put(Steps.STEP_IMAGE_URL,step.getImageUrl());
        values.put(Steps.STEP_RECIPE_ID,recipeId);
        return values;
    }

    public static RecipeEntity toRecipe(Cursor cursor){
        if(cursor==null) return null;
        RecipeEntity recipe=new RecipeEntity();
        String imageUrl=cursor.getString(cursor.getColumnIndex(Recipes.RECIPE_IMAGE_URL));
        String recipeName=cursor.getString(cursor.getColumnIndex(Recipes.RECIPE_NAME));
        int recipeId=cursor.getInt(cursor.getColumnIndex(Recipes.RECIPE_ID));
        int recipeServings=cursor.getInt(cursor.getColumnIndex(Recipes.RECIPE_SERVINGS));
        recipe.setImageUrl(imageUrl);
        recipe.setId(recipeId);
        recipe.setServings(recipeServings);
        recipe.setName(recipeName);
        return recipe;
    }

    public static IngredientEntity toIngredient(Cursor cursor){
        if(cursor==null) return null;
        int ingredientId=cursor.getInt(cursor.getColumnIndex(Ingredients.INGREDIENT_ID));
        String ingredientName=cursor.getString(cursor.getColumnIndex(Ingredients.INGREDIENT_NAME));
        String measure=cursor.getString(cursor.getColumnIndex(Ingredients.INGREDIENT_MEASURE));
        float quantity=cursor.getFloat(cursor.getColumnIndex(Ingredients.INGREDIENT_QUANTITY));
        IngredientEntity entity=new IngredientEntity();
        entity.setIngredient(ingredientName);
        entity.setMeasure(measure);
        entity.setId(ingredientId);
        entity.setQuantity(quantity);
        return entity;
    }

    public static StepEntity toStep(Cursor cursor){
        if(cursor==null) return null;
        int stepId=cursor.getInt(cursor.getColumnIndex(Steps.STEP_ID));
        String videoUrl=cursor.getString(cursor.getColumnIndex(Steps.STEP_VIDEO_URL));
        String imageUrl=cursor.getString(cursor.getColumnIndex(Steps.STEP_IMAGE_URL));
        String description=cursor.getString(cursor.getColumnIndex(Steps.STEP_DESCRIPTION));
        String shortDescription=cursor.getString(cursor.getColumnIndex(Steps.STEP_SHORT_DESCRIPTION));
        StepEntity step=new StepEntity();
        step.setId(stepId);
        step.setDescription(description);
        step.setShortDescription(shortDescription);
        step.setImageUrl(imageUrl);
        step.setVideoUrl(videoUrl);
        return step;
    }

}

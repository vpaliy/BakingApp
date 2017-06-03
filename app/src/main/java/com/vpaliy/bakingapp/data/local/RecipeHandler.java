package com.vpaliy.bakingapp.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;

import java.util.ArrayList;
import java.util.List;

import static com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import static com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper.RecipesIngredients;

class RecipeHandler {

    private static final String TAG=RecipeHandler.class.getSimpleName();

    private ContentResolver contentResolver;

    private RecipeHandler(ContentResolver resolver){
        this.contentResolver=resolver;
    }

    public static RecipeHandler start(ContentResolver resolver){
        return new RecipeHandler(resolver);
    }

    public RecipeHandler insert(RecipeEntity recipeEntity){
        if(recipeEntity!=null){
            ContentValues values=DatabaseUtils.toValues(recipeEntity);
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
                Uri contentUri = Recipes.buildRecipeWithIngredientsUri(Integer.toString(recipeId));
                contentResolver.insert(contentUri, values);
                contentUri=Ingredients.buildIngredientWithRecipesUri(Integer.toString(ingredient.getId()));
                contentResolver.insert(contentUri,values);
                contentResolver.insert(Ingredients.CONTENT_URI, DatabaseUtils.toValues(ingredient));
            });
        }
        return this;
    }

    public List<RecipeEntity> queryAll(){
        Cursor cursor=contentResolver.query(Recipes.CONTENT_URI,null,null,null,null);
        if(cursor!=null){
            Log.d(TAG,"Cursor for RecipeEntity size:"+Integer.toString(cursor.getCount()));
            List<RecipeEntity> recipes=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                RecipeEntity recipeEntity=DatabaseUtils.toRecipe(cursor);
                queryStepsFor(recipeEntity).queryIngredientsFor(recipeEntity);
                recipes.add(recipeEntity);
            }
            return recipes;
        }
        return null;
    }

    public RecipeHandler queryStepsFor(RecipeEntity entity){
        if(entity!=null){
            Log.d(TAG,"Query steps for:"+Integer.toString(entity.getId()));
            Uri contentUri=Recipes.buildRecipeWithStepsUri(Integer.toString(entity.getId()));
            Cursor cursor=contentResolver.query(contentUri,null,null,null,null);
            if(cursor!=null){
                Log.d(TAG,"Cursor count:"+Integer.toString(cursor.getCount()));
                List<StepEntity> steps=new ArrayList<>(cursor.getCount());
                while(cursor.moveToNext()){
                    steps.add(DatabaseUtils.toSteps(cursor));
                }
                entity.setSteps(steps);
                if(!cursor.isClosed()) cursor.close();
            }
        }
        return this;
    }

    public RecipeHandler queryIngredientsFor(RecipeEntity recipe){
        if(recipe!=null){
            Log.d(TAG,"Query ingredients for :"+recipe.getId());
            Uri contentUri=Recipes.buildRecipeWithIngredientsUri(Integer.toString(recipe.getId()));
            Cursor cursor=contentResolver.query(contentUri,null,null,null,null);
            if(cursor!=null){
                Log.d(TAG,"Cursor count:"+Integer.toString(cursor.getCount()));
                List<IngredientEntity> ingredients=new ArrayList<>(cursor.getCount());
                while(cursor.moveToNext()){
                    ingredients.add(DatabaseUtils.toIngredient(cursor));
                }
                if(!cursor.isClosed()) cursor.close();
                recipe.setIngredients(ingredients);
            }
        }
        return this;
    }

    public RecipeHandler insertSteps(int recipeId, List<StepEntity> steps){
        if (steps != null) {
            steps.forEach(stepEntity -> {
                ContentValues values=DatabaseUtils.toValues(stepEntity,recipeId);
                contentResolver.insert(Steps.CONTENT_URI,values);
                values.clear();
            });
        }
        return this;
    }

}

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
import static com.vpaliy.bakingapp.data.local.RecipeDatabase.RecipesIngredients;

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

    public List<RecipeEntity> queryAll(){
        Cursor cursor=contentResolver.query(Recipes.CONTENT_URI,null,null,null,null);
        if(cursor!=null){
            Log.d(TAG,"Cursor for RecipeEntity size:"+Integer.toString(cursor.getCount()));
            List<RecipeEntity> recipes=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                RecipeEntity recipeEntity=convertToRecipe(cursor);
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
                    int stepId=cursor.getInt(cursor.getColumnIndex(Steps.STEP_ID));
                    String videoUrl=cursor.getString(cursor.getColumnIndex(Steps.STEP_VIDEO_URL));
                    String imageUrl=cursor.getString(cursor.getColumnIndex(Steps.STEP_IMAGE_URL));
                    String description=cursor.getString(cursor.getColumnIndex(Steps.STEP_DESCRIPTION));
                    String shortDescription=cursor.getString(cursor.getColumnIndex(Steps.STEP_SHORT_DESCRIPTION));
                    Log.d(TAG,"#Step description:"+description);
                    Log.d(TAG,"#Step id:"+stepId);
                    Log.d(TAG,"#Step image url:"+imageUrl);
                    Log.d(TAG,"#Step short description:"+shortDescription);
                    Log.d(TAG,"#Step video url:"+videoUrl);
                    StepEntity step=new StepEntity();
                    step.setId(stepId);
                    step.setDescription(description);
                    step.setShortDescription(shortDescription);
                    step.setImageUrl(imageUrl);
                    step.setVideoUrl(videoUrl);
                    steps.add(step);
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
            Uri contentUri=Recipes.buildRecipeWithIngredienstUri(Integer.toString(recipe.getId()));
            Cursor cursor=contentResolver.query(contentUri,null,null,null,null);
            if(cursor!=null){
                Log.d(TAG,"Cursor count:"+Integer.toString(cursor.getCount()));
                List<IngredientEntity> ingredients=new ArrayList<>(cursor.getCount());
                while(cursor.moveToNext()){
                    int ingredientId=cursor.getInt(cursor.getColumnIndex(Ingredients.INGREDIENT_ID));
                    String ingredientName=cursor.getString(cursor.getColumnIndex(Ingredients.INGREDIENT_NAME));
                    String measure=cursor.getString(cursor.getColumnIndex(Ingredients.INGREDIENT_MEASURE));
                    float quantity=cursor.getFloat(cursor.getColumnIndex(Ingredients.INGREDIENT_QUANTITY));

                    Log.d(TAG,"#Ingredient id:"+Integer.toString(ingredientId));
                    Log.d(TAG,"#Ingredient name:"+ingredientName);
                    Log.d(TAG,"#Ingredient measure:"+measure);
                    Log.d(TAG,"#Ingredient quantity:"+Float.toString(quantity));

                    IngredientEntity entity=new IngredientEntity();
                    entity.setIngredient(ingredientName);
                    entity.setMeasure(measure);
                    entity.setId(ingredientId);
                    entity.setQuantity(quantity);
                    ingredients.add(entity);
                }
                if(!cursor.isClosed()) cursor.close();
                recipe.setIngredients(ingredients);
            }
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

    private RecipeEntity convertToRecipe(Cursor cursor){
        if(cursor!=null){
            RecipeEntity recipe=new RecipeEntity();
            String imageUrl=cursor.getString(cursor.getColumnIndex(Recipes.RECIPE_IMAGE_URL));
            String recipeName=cursor.getString(cursor.getColumnIndex(Recipes.RECIPE_NAME));
            int recipeId=cursor.getInt(cursor.getColumnIndex(Recipes.RECIPE_ID));
            int recipeServings=cursor.getInt(cursor.getColumnIndex(Recipes.RECIPE_SERVINGS));
            Log.d(TAG,"#Recipe image:"+imageUrl);
            Log.d(TAG,"#Recipe servings:"+Integer.toString(recipeServings));
            Log.d(TAG,"#Recipe id:"+Integer.toString(recipeId));
            Log.d(TAG,"#Recipe name:"+recipeName);
            recipe.setImageUrl(imageUrl);
            recipe.setId(recipeId);
            recipe.setServings(recipeServings);
            recipe.setName(recipeName);
            return recipe;
        }
        return null;
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

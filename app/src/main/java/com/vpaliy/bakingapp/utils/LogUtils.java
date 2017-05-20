package com.vpaliy.bakingapp.utils;


import android.util.Log;

import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import java.util.List;

public class LogUtils {

    private LogUtils(){
        throw new UnsupportedOperationException();
    }

    public static void logD(Object object,Class<?> clazz){
        Log.d(clazz.getSimpleName(),object.toString());
    }

    public static void logD(List<Recipe> recipes,Object clazz){
        final String TAG=clazz.getClass().getSimpleName()+":";
        if(recipes==null){
            Log.d(TAG,"Recipes are null");
            return;
        }
        Log.d(TAG,"Size is:"+Integer.toString(recipes.size()));
        recipes.forEach(recipe -> logD(recipe,TAG));
    }

    public static void log(List<RecipeEntity> recipes, Object clazz){
        final String TAG=clazz.getClass().getSimpleName()+":";
        if(recipes==null){
            Log.d(TAG,"Recipes are null");
            return;
        }
        Log.d(TAG,"Size is:"+Integer.toString(recipes.size()));
        recipes.forEach(recipeEntity -> logD(recipeEntity,TAG));
    }

    public static void logD(Recipe recipe, String TAG){
        if(recipe==null) {
            Log.d(TAG,"#recipe is null");
            return;
        }
        Log.d(TAG,"#recipe id:"+Integer.toString(recipe.getId()));
        Log.d(TAG,"#recipe name:"+recipe.getName());
        Log.d(TAG,"#recipe image url:"+recipe.getImageUrl());
        Log.d(TAG,"#recipe servings:"+Integer.toString(recipe.getServings()));
        Log.d(TAG,"-------------------------------------------");
        logDI(recipe.getIngredients(),TAG);
        logD(recipe.getSteps(),TAG);
    }

    public static void logD(RecipeEntity recipe, String TAG){
        if(recipe==null) {
            Log.d(TAG,"#recipe is null");
            return;
        }
        Log.d(TAG,"#recipe id:"+Integer.toString(recipe.getId()));
        Log.d(TAG,"#recipe name:"+recipe.getName());
        Log.d(TAG,"#recipe image url:"+recipe.getImageUrl());
        Log.d(TAG,"#recipe servings:"+Integer.toString(recipe.getServings()));
        log(recipe.getIngredients(),TAG);
        logDS(recipe.getSteps(),TAG);
    }

    public static void logD(Ingredient ingredient, String TAG){
        if(ingredient==null){
            Log.d(TAG,"#ingredient is null");
            return;
        }
        Log.d(TAG,"#ingredient id:"+Integer.toString(ingredient.getId()));
        Log.d(TAG,"#ingredient ingredient:"+ingredient.getIngredient());
        Log.d(TAG,"#ingredient measure:"+ingredient.getMeasure());
        Log.d(TAG,"#ingredient quantity:"+ingredient.getQuantity());
    }

    public static void logD(IngredientEntity ingredient, String TAG){
        if(ingredient==null){
            Log.d(TAG,"#ingredient is null");
            return;
        }
        Log.d(TAG,"#ingredient id:"+Integer.toString(ingredient.getId()));
        Log.d(TAG,"#ingredient ingredient:"+ingredient.getIngredient());
        Log.d(TAG,"#ingredient measure:"+ingredient.getMeasure());
        Log.d(TAG,"#ingredient quantity:"+ingredient.getQuantity());
    }

    public static void logDI(List<Ingredient> ingredients, String TAG){
        if(ingredients==null){
            Log.d(TAG,"Ingredients are null");
            return;
        }
        Log.d(TAG,"Size is:"+Integer.toString(ingredients.size()));
        ingredients.forEach(ingredient ->  logD(ingredient,TAG));
    }

    public static void log(List<IngredientEntity> ingredients, String TAG){
        if(ingredients==null){
            Log.d(TAG,"Ingredients are null");
            return;
        }
        Log.d(TAG,"Size is:"+Integer.toString(ingredients.size()));
        ingredients.forEach(ingredientEntity ->logD(ingredientEntity,TAG));
    }

    public static void logD(List<Step> steps, String TAG){
        if(steps==null){
            Log.d(TAG,"Steps are null");
            return;
        }
        Log.d(TAG,"Size is:"+Integer.toString(steps.size()));
        steps.forEach(step -> logD(step,TAG));
    }

    public static void logDS(List<StepEntity> steps, String TAG){
        if(steps==null){
            Log.d(TAG,"Steps are null");
            return;
        }
        Log.d(TAG,"Size is:"+Integer.toString(steps.size()));
        steps.forEach(step -> logD(step,TAG));
    }

    public static void logD(Step step,String TAG){
        if(step==null){
            Log.d(TAG,"#step is null");
            return;
        }
        Log.d(TAG,"#step id:"+step.getStepId());
        Log.d(TAG,"#step image url:"+step.getImageUrl());
        Log.d(TAG,"#step description:"+step.getDescription());
        Log.d(TAG,"#step short desc:"+step.getShortDescription());
        Log.d(TAG,"#step video url:"+step.getVideoUrl());
    }

    public static void logD(StepEntity step, String TAG){
        if(step==null){
            Log.d(TAG,"#step is null");
            return;
        }
        Log.d(TAG,"#step id:"+step.getId());
        Log.d(TAG,"#step image url:"+step.getImageUrl());
        Log.d(TAG,"#step description:"+step.getDescription());
        Log.d(TAG,"#step short desc:"+step.getShortDescription());
        Log.d(TAG,"#step video url:"+step.getVideoUrl());
    }

}

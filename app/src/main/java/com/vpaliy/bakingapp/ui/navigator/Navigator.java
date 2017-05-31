package com.vpaliy.bakingapp.ui.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vpaliy.bakingapp.ui.activity.RecipeDetailsActivity;
import com.vpaliy.bakingapp.ui.bus.event.OnRecipeClickEvent;
import com.vpaliy.bakingapp.utils.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    private static final String TAG=Navigator.class.getSimpleName();

    @Inject
    public Navigator(){}

    public void navigateToRecipeDetails(@NonNull Activity activity,
                                        @NonNull OnRecipeClickEvent onRecipeClickEvent){
        Log.d(TAG,"Navigate to details activity");
        Intent intent=new Intent(activity, RecipeDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_RECIPE_ID,onRecipeClickEvent.recipeId);
        activity.startActivity(intent);
    }
}
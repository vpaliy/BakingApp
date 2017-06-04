package com.vpaliy.bakingapp.ui.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.vpaliy.bakingapp.ui.activity.DetailsActivity;
import com.vpaliy.bakingapp.ui.bus.event.OnRecipeClickEvent;
import com.vpaliy.bakingapp.utils.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    @Inject
    public Navigator(){}

    public void navigateToRecipeDetails(@NonNull Activity activity,
                                        @NonNull OnRecipeClickEvent onRecipeClickEvent){
        Intent intent=new Intent(activity, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_RECIPE_ID,onRecipeClickEvent.recipeId);
        activity.startActivity(intent);
    }
}
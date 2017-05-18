package com.vpaliy.bakingapp.data.remote;


import android.support.annotation.NonNull;
import javax.inject.Inject;

public class RemoteRecipeSource {

    private RecipeAPI recipeAPI;

    @Inject
    public RemoteRecipeSource(@NonNull RecipeAPI recipeAPI){
        this.recipeAPI=recipeAPI;
    }


}

package com.vpaliy.bakingapp.data.remote;


import com.vpaliy.bakingapp.domain.model.Recipe;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface RecipeAPI {

    @GET("/android-baking-app-json ")
    Observable<List<Recipe>> queryRecipes();
}

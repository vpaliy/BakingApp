package com.vpaliy.bakingapp.data.remote;


import com.vpaliy.bakingapp.data.model.RecipeEntity;
import java.util.List;
import rx.Observable;
import retrofit2.http.GET;

public interface RecipeAPI {
    @GET("/android-baking-app-json ")
    Observable<List<RecipeEntity>> queryRecipes();
}

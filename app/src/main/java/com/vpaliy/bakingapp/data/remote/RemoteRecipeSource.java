package com.vpaliy.bakingapp.data.remote;

import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.utils.LogUtils;

import java.util.List;
import rx.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

import android.support.annotation.NonNull;

@Singleton
public class RemoteRecipeSource extends DataSource<RecipeEntity> {

    private final RecipeAPI recipeAPI;

    @Inject
    public RemoteRecipeSource(@NonNull RecipeAPI recipeAPI){
        this.recipeAPI=recipeAPI;
    }

    @Override
    public Observable<List<RecipeEntity>> getRecipes() {
        return recipeAPI.queryRecipes()
                .doOnNext(list->LogUtils.log(list,this));
    }

    @Override
    public void insert(RecipeEntity item) {}
}

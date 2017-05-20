package com.vpaliy.bakingapp.data.local;


import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalRecipeSource extends DataSource<RecipeEntity> {

    @Inject
    public LocalRecipeSource(){}

    @Override
    public Observable<List<RecipeEntity>> getRecipes() {
        return null;
    }

}

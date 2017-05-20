package com.vpaliy.bakingapp.data.local;


import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalRecipeSource extends DataSource<RecipeEntity> {

    private final ContentResolver contentResolver;

    @Inject
    public LocalRecipeSource(@NonNull Context context){
        this.contentResolver=context.getContentResolver();
    }

    @Override
    public Observable<List<RecipeEntity>> getRecipes() {
        return null;
    }

    @Override
    public void insert(RecipeEntity item) {
        if(item!=null) {
            int recipeId=item.getId();
            RecipeHandler.start(contentResolver)
                    .insert(item)
                    .insertIngredients(recipeId, item.getIngredients())
                    .insertSteps(recipeId, item.getSteps());
        }
    }
}

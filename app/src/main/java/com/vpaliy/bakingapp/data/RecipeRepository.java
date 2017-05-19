package com.vpaliy.bakingapp.data;


import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import rx.Observable;
import java.util.List;

import android.support.annotation.NonNull;
import javax.inject.Inject;

public class RecipeRepository implements IRepository<Recipe> {

    private Mapper<Recipe,RecipeEntity> mapper;

    @Inject
    public RecipeRepository(@NonNull Mapper<Recipe,RecipeEntity> mapper){
        this.mapper=mapper;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return null;
    }

    @Override
    public Observable<Recipe> getRecipeById(int recipeId) {
        return null;
    }
}

package com.vpaliy.bakingapp.domain;


import com.vpaliy.bakingapp.domain.model.Recipe;

import java.util.List;

import rx.Observable;

public interface IRepository<T>  {
    Observable<List<T>> getRecipes();
    Observable<Recipe> getRecipeById(int recipeId);
}

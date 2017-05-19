package com.vpaliy.bakingapp.domain;



import java.util.List;
import rx.Observable;

public interface IRepository<T>  {
    Observable<List<T>> getRecipes();
    Observable<T> getRecipeById(int id);
}

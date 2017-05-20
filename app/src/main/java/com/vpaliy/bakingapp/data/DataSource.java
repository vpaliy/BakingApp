package com.vpaliy.bakingapp.data;

import com.vpaliy.bakingapp.domain.IRepository;

import rx.Observable;

public abstract class DataSource<T> implements IRepository<T> {
    @Override
    public Observable<T> getRecipeById(int id) {
        return null;
    }
}

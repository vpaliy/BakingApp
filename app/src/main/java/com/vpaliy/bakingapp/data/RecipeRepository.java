package com.vpaliy.bakingapp.data;


import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.LogUtils;

import rx.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import javax.inject.Inject;

public class RecipeRepository implements IRepository<Recipe> {

    private final Mapper<Recipe,RecipeEntity> mapper;
    private final DataSource<RecipeEntity> localDataSource;
    private final DataSource<RecipeEntity> remoteDataSource;
    private final Context context;

    private SparseArray<Recipe> inMemoryCache;

    @Inject
    public RecipeRepository(@NonNull DataSource<RecipeEntity> localDataSource,
                            @NonNull DataSource<RecipeEntity> remoteDataSource,
                            @NonNull Mapper<Recipe,RecipeEntity> mapper,
                            @NonNull Context context){
        this.localDataSource=localDataSource;
        this.remoteDataSource=remoteDataSource;
        this.mapper=mapper;
        this.context=context;
        this.inMemoryCache=new SparseArray<>();
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        if(isNetworkConnection()){
            if(inMemoryCache.size()>100) inMemoryCache.clear();
            return remoteDataSource.getRecipes()
                    .doOnNext(this::saveToDisk)
                    .map(mapper::map)
                    .doOnNext(recipes -> Observable.from(recipes)
                            .filter(recipe->inMemoryCache.get(recipe.getId())!=null)
                            .map(recipe -> {
                                inMemoryCache.put(recipe.getId(),recipe);
                                return recipe;
                            }))
                    .doOnNext(list-> LogUtils.logD(list,this));
        }
        //TODO query the local database
        return null;
    }

    private void saveToDisk(List<RecipeEntity> list){
        //TODO save data to the database
    }

    @Override
    public Observable<Recipe> getRecipeById(int id) {
        if(inMemoryCache.get(id)!=null) {
            return Observable.just(inMemoryCache.get(id));
        }
        //TODO query the database
        return null;
    }


    private boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
}

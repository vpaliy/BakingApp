package com.vpaliy.bakingapp.data;


import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.LogUtils;

import rx.Observable;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import javax.inject.Inject;

public class RecipeRepository implements IRepository<Recipe> {

    private final Mapper<Recipe,RecipeEntity> mapper;
    private final DataSource<RecipeEntity> localDataSource;
    private final DataSource<RecipeEntity> remoteDataSource;
    private final Context context;

    @Inject
    public RecipeRepository(@NonNull DataSource<RecipeEntity> localDataSource,
                            @NonNull DataSource<RecipeEntity> remoteDataSource,
                            @NonNull Mapper<Recipe,RecipeEntity> mapper,
                            @NonNull Context context){
        this.localDataSource=localDataSource;
        this.remoteDataSource=remoteDataSource;
        this.mapper=mapper;
        this.context=context;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        if(isNetworkConnection()){
            return remoteDataSource.getRecipes()
                    .doOnNext(this::saveToDisk)
                    .map(mapper::map)
                    .doOnNext(list-> LogUtils.logD(list,this));
        }
        return null;
    }

    private void saveToDisk(List<RecipeEntity> list){

    }

    @Override
    public Observable<Recipe> getRecipeById(int recipeId) {
        return null;
    }


    private boolean isNetworkConnection(){
        return false;
    }
}

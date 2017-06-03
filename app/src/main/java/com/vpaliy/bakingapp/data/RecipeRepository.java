package com.vpaliy.bakingapp.data;

import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.LogUtils;
import rx.Observable;
import java.util.List;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.SparseArray;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.vpaliy.bakingapp.data.annotation.Local;
import com.vpaliy.bakingapp.data.annotation.Remote;

@Singleton
public class RecipeRepository implements IRepository<Recipe> {

    private static final String TAG=RecipeRepository.class.getSimpleName();

    private final Mapper<Recipe,RecipeEntity> mapper;
    private final DataSource<RecipeEntity> localDataSource;
    private final DataSource<RecipeEntity> remoteDataSource;
    private final Context context;

    private SparseArray<Recipe> inMemoryCache;

    @Inject
    public RecipeRepository(@NonNull @Local DataSource<RecipeEntity> localDataSource,
                            @NonNull @Remote DataSource<RecipeEntity> remoteDataSource,
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
            return remoteDataSource.getRecipes()
                    .doOnNext(this::saveToDisk)
                    .map(mapper::map)
                    .doOnNext(this::cacheData)
                    .doOnNext(list-> LogUtils.logD(list,this));
        }
        return localDataSource.getRecipes()
                .map(mapper::map)
                .doOnNext(this::cacheData)
                .doOnNext(list->LogUtils.logD(list,this));
    }

    private void cacheData(List<Recipe> recipeList){
        if(inMemoryCache.size()>=100) inMemoryCache.clear();
        recipeList.forEach(recipe ->{
            if(inMemoryCache.get(recipe.getId())==null){
                inMemoryCache.put(recipe.getId(),recipe);
            }
        });
    }

    private void saveToDisk(List<RecipeEntity> list){
        if(list!=null){
            list.forEach(localDataSource::insert);
        }
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

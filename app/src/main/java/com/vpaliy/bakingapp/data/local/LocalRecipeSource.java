package com.vpaliy.bakingapp.data.local;

import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import java.util.List;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalRecipeSource extends DataSource<RecipeEntity> {

    private RecipeHandler handler;

    @Inject
    public LocalRecipeSource(RecipeHandler handler){
        this.handler=handler;
    }

    @Override
    public Observable<List<RecipeEntity>> getRecipes() {
        return Observable.fromCallable(()->handler.queryAll());
    }

    @Override
    public void insert(RecipeEntity item) {
        if(item!=null) {
            int recipeId=item.getId();
            handler.insert(item)
                    .insertIngredients(recipeId, item.getIngredients())
                    .insertSteps(recipeId, item.getSteps());
        }
    }
}

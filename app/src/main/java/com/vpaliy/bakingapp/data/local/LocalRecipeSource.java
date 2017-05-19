package com.vpaliy.bakingapp.data.local;


import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import java.util.List;
import rx.Observable;

public class LocalRecipeSource extends DataSource<RecipeEntity> {

    @Override
    public Observable<List<RecipeEntity>> getRecipes() {
        return null;
    }

}

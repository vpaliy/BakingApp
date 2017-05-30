package com.vpaliy.bakingapp.di.module;

import com.vpaliy.bakingapp.mvp.contract.RecipeDetailsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import com.vpaliy.bakingapp.mvp.presenter.RecipeDetailsPresenter;
import com.vpaliy.bakingapp.mvp.presenter.RecipesPresenter;

import com.vpaliy.bakingapp.di.scope.ViewScope;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ViewScope
    @Provides
    RecipesContract.Presenter recipesPresenter(RecipesPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    RecipeDetailsContract.Presenter recipeDetailsPresenter(RecipeDetailsPresenter presenter){
        return presenter;
    }

}

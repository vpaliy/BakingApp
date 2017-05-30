package com.vpaliy.bakingapp.di.module;

import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import com.vpaliy.bakingapp.mvp.presenter.RecipeSummaryPresenter;
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
    RecipeSummaryContract.Presenter recipeDetailsPresenter(RecipeSummaryPresenter presenter){
        return presenter;
    }

}

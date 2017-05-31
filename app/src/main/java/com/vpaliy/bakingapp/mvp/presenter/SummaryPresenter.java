package com.vpaliy.bakingapp.mvp.presenter;


import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract.View;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import com.vpaliy.bakingapp.di.scope.ViewScope;

@ViewScope
public class SummaryPresenter extends BaseRecipePresenter
        implements RecipeSummaryContract.Presenter{

    private View view;

    @Inject
    public SummaryPresenter(@NonNull IRepository<Recipe> repository,
                            @NonNull BaseSchedulerProvider schedulerProvider,
                            @NonNull MessageProvider messageProvider){
        super(repository,schedulerProvider,messageProvider);
    }

    @Override
    public void attachView(@NonNull View view) {
        //TODO check for null
        this.view=view;
    }

    @Override
    public void stop() {
        subscriptions.clear();
    }

    @Override
    public void fetchById(int recipeId) {
        subscriptions.add(repository.getRecipeById(recipeId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processRecipe,this::catchError,()->{}));
    }

    private void processRecipe(Recipe recipe){
        if(recipe==null){
            view.showMessage(messageProvider.emptyMessage());
            return;
        }
        view.showRecipe(recipe);
    }

    private void catchError(Throwable error){
        error.printStackTrace();
        view.showMessage(messageProvider.errorHasOccurred());
    }
}

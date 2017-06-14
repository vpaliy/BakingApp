package com.vpaliy.bakingapp.mvp.presenter;


import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.View;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import java.util.List;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import com.vpaliy.bakingapp.di.scope.ViewScope;

import static com.google.android.exoplayer2.util.Assertions.checkNotNull;

@ViewScope
public class RecipesPresenter extends BaseRecipePresenter
        implements RecipesContract.Presenter {

    private View view;

    @Inject
    public RecipesPresenter(@NonNull IRepository<Recipe> repository,
                            @NonNull BaseSchedulerProvider schedulerProvider,
                            @NonNull MessageProvider messageProvider){
        super(repository,schedulerProvider,messageProvider);
    }

    @Override
    public void attachView(@NonNull View view) {
        checkNotNull(view);
        this.view=view;
    }

    @Override
    public void queryRecipes() {
        subscriptions.clear();
        load();
    }

    private void load(){
        view.setLoading(true);
        subscriptions.add(repository.getRecipes()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processData,this::catchError,this::complete));
    }

    private void processData(List<Recipe> recipes){
        if(recipes==null||recipes.isEmpty()){
            view.showErrorMessage(messageProvider.emptyMessage());
            return;
        }
        view.showRecipes(recipes);
    }

    private void catchError(Throwable error){
        subscriptions.clear();
        error.printStackTrace();
        view.setLoading(false);
        view.showErrorMessage(messageProvider.noNetworkConnection());
    }

    private void complete(){
        view.setLoading(false);
    }

    @Override
    public void start() {
        load();
    }

    @Override
    public void stop() {
        subscriptions.clear();
    }
}

package com.vpaliy.bakingapp.mvp.presenter;


import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.View;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import java.util.List;
import rx.subscriptions.CompositeSubscription;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import com.vpaliy.bakingapp.di.scope.ViewScope;

@ViewScope
public class RecipesPresenter implements RecipesContract.Presenter {

    private final IRepository<Recipe> repository;
    private final BaseSchedulerProvider schedulerProvider;
    private final CompositeSubscription subscriptions;
    private final MessageProvider messageProvider;
    private View view;

    @Inject
    public RecipesPresenter(@NonNull IRepository<Recipe> repository,
                            @NonNull BaseSchedulerProvider schedulerProvider,
                            @NonNull MessageProvider messageProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.messageProvider=messageProvider;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void attachView(@NonNull View view) {
        //TODO add check for null
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
            view.showMessage(messageProvider.emptyMessage());
            return;
        }
        view.showRecipes(recipes);
    }

    private void catchError(Throwable error){
        error.printStackTrace();
        view.showMessage(messageProvider.noNetworkConnection());
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

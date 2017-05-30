package com.vpaliy.bakingapp.mvp.presenter;


import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import rx.subscriptions.CompositeSubscription;

import android.support.annotation.NonNull;

class BaseRecipePresenter {

    protected final IRepository<Recipe> repository;
    protected final BaseSchedulerProvider schedulerProvider;
    protected final CompositeSubscription subscriptions;
    protected final MessageProvider messageProvider;

    BaseRecipePresenter(@NonNull IRepository<Recipe> repository,
                               @NonNull BaseSchedulerProvider schedulerProvider,
                               @NonNull MessageProvider messageProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.messageProvider=messageProvider;
        this.subscriptions=new CompositeSubscription();
    }
}

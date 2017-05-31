package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.navigator.Navigator;

import android.support.annotation.Nullable;
import javax.inject.Inject;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected Navigator navigator;

    @Inject
    protected RxBus eventBus;

    protected CompositeDisposable disposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables=new CompositeDisposable();
        initializeDependencies();
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        disposables.add(eventBus.asFlowable()
                .subscribe(this::processEvent));
    }

    private void processEvent(Object object){
        if(object!=null){
            handleEvent(object);
        }
    }

    @CallSuper
    @Override
    protected void onStop(){
        super.onStop();
        disposables.clear();
    }

    abstract void handleEvent(@NonNull Object event);

    abstract void initializeDependencies();
}

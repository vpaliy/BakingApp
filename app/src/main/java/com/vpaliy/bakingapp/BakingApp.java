package com.vpaliy.bakingapp;

import android.app.Application;

import com.vpaliy.bakingapp.di.component.ApplicationComponent;
import com.vpaliy.bakingapp.di.component.DaggerApplicationComponent;
import com.vpaliy.bakingapp.di.module.ApplicationModule;
import com.vpaliy.bakingapp.di.module.DataModule;
import com.vpaliy.bakingapp.di.module.NetworkModule;

public class BakingApp extends Application {

    private static BakingApp INSTANCE;
    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
        installAppComponent();
    }

    private void installAppComponent(){
        appComponent= DaggerApplicationComponent.builder()
                .dataModule(new DataModule())
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule()).build();
    }

    public ApplicationComponent appComponent(){
        return appComponent;
    }

    public static BakingApp appInstance(){
        return INSTANCE;
    }

}

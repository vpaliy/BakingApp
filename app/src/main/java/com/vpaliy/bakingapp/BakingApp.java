package com.vpaliy.bakingapp;

import android.app.Application;

import com.vpaliy.bakingapp.di.component.ApplicationComponent;

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
        //TODO install the component
    }

    public ApplicationComponent appComponent(){
        return appComponent;
    }

    public static BakingApp appInstance(){
        return INSTANCE;
    }

}

package com.vpaliy.bakingapp;

import android.app.Application;

import com.vpaliy.bakingapp.di.component.ApplicationComponent;
import com.vpaliy.bakingapp.di.component.DaggerApplicationComponent;
import com.vpaliy.bakingapp.di.module.ApplicationModule;
import com.vpaliy.bakingapp.di.module.DataModule;
import com.vpaliy.bakingapp.di.module.NetworkModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BakingApp extends Application {

    private static BakingApp INSTANCE;
    private ApplicationComponent appComponent;
    private final static String ROBOTO_SLAB = "fonts/RobotoSlab-Regular.ttf";

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
        installAppComponent();
        configureDefaultFont(ROBOTO_SLAB);
    }

    private void configureDefaultFont(String robotoSlab) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(robotoSlab)
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void installAppComponent(){
        appComponent= DaggerApplicationComponent.builder()
                .dataModule(new DataModule())
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule()).build();
    }

    public void setAppComponent(ApplicationComponent appComponent) {
        this.appComponent = appComponent;

    }

    public ApplicationComponent appComponent(){
        return appComponent;
    }

    public static BakingApp appInstance(){
        return INSTANCE;
    }

}

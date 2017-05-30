package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.vpaliy.bakingapp.ui.navigator.Navigator;
import android.support.annotation.Nullable;
import javax.inject.Inject;


public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencies();
    }

    abstract void initializeDependencies();
}

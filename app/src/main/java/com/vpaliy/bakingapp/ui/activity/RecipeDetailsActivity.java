package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import com.vpaliy.bakingapp.BakingApp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RecipeDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void handleEvent(@NonNull Object event) {

    }

    @Override
    void initializeDependencies() {
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }
}

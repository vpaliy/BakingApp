package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.ui.fragment.RecipeSummaryFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RecipeDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            setUI(getIntent().getExtras());
        }

    }

    private void setUI(Bundle args){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, RecipeSummaryFragment.newInstance(args))
                .commit();
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

package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.ui.bus.event.OnRecipeClickEvent;
import com.vpaliy.bakingapp.ui.fragment.RecipesFragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends BaseActivity {

    @BindView(R.id.action_bar)
    protected Toolbar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(savedInstanceState==null) {
            setUI();
        }
    }

    private void setUI(){
        setSupportActionBar(actionBar);
        if(getSupportActionBar()!=null){
            actionBar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
            getSupportActionBar().setTitle(R.string.recipes_label);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,new RecipesFragment())
                .commit();
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof OnRecipeClickEvent){
            showDetails(OnRecipeClickEvent.class.cast(event));
        }
    }

    private void showDetails(OnRecipeClickEvent clickEvent){
        navigator.navigateToRecipeDetails(this,clickEvent);
    }

    @Override
    void initializeDependencies() {
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }
}

package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.ui.bus.event.OnRecipeClickEvent;
import com.vpaliy.bakingapp.ui.fragment.RecipesFragment;
import android.util.Log;
import android.support.annotation.NonNull;

public class RecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null) {
            setUI();
        }
    }

    private void setUI(){
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
        Log.d(RecipeActivity.class.getSimpleName(),"showDetails()");
        navigator.navigateToRecipeDetails(this,clickEvent);
    }

    @Override
    void initializeDependencies() {
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }
}

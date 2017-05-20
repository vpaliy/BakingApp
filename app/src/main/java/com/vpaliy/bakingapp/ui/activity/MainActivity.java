package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    IRepository<Recipe> recipeIRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BakingApp.appInstance().appComponent().inject(this);
        //enjoy
        recipeIRepository.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    if(recipes!=null)
                    Log.d(MainActivity.class.getSimpleName(),Integer.toString(recipes.size()));
                });
    }
}

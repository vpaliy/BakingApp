package com.vpaliy.bakingapp.mvp.presenter;


import android.support.annotation.NonNull;
import com.vpaliy.bakingapp.mvp.contract.RecipeDetailsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeDetailsContract.View;

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter{

    private View view;

    @Override
    public void attachView(@NonNull View view) {
        //TODO check for null
        this.view=view;
    }


}

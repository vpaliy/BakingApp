package com.vpaliy.bakingapp.ui.fragment;


import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract.Presenter;
import com.vpaliy.bakingapp.utils.Constants;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

public class RecipeSummaryFragment extends BaseFragment
        implements RecipeSummaryContract.View{

    private Presenter presenter;
    private int recipeId;

    public static RecipeSummaryFragment newInstance(Bundle bundle){
        RecipeSummaryFragment fragment=new RecipeSummaryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        this.recipeId=savedInstanceState.getInt(Constants.EXTRA_RECIPE_ID);
    }

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(BakingApp.appInstance().appComponent())
                .build().inject(this);
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showRecipe(@NonNull Recipe recipe) {

    }
}

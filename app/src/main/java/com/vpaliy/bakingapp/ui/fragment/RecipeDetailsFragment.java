package com.vpaliy.bakingapp.ui.fragment;


import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.contract.RecipeDetailsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeDetailsContract.Presenter;
import android.support.annotation.NonNull;

public class RecipeDetailsFragment extends BaseFragment
        implements RecipeDetailsContract.View{

    private Presenter presenter;

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(BakingApp.appInstance().appComponent())
                .build().inject(this);
    }

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

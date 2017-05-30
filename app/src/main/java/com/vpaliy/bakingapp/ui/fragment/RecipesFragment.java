package com.vpaliy.bakingapp.ui.fragment;

import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import java.util.List;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.Presenter;

import android.support.annotation.NonNull;

public class RecipesFragment extends BaseFragment
        implements RecipesContract.View{

    private Presenter presenter;

    @Override
    public void attachPresenter(@NonNull RecipesContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void setLoading(boolean isLoading) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showRecipes(@NonNull List<Recipe> recipes) {

    }

    @Override
    void initializeDependencies() {

    }
}

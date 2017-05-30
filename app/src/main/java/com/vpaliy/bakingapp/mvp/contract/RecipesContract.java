package com.vpaliy.bakingapp.mvp.contract;

import android.support.annotation.NonNull;

import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.BasePresenter;
import com.vpaliy.bakingapp.mvp.BaseView;

import java.util.List;

public interface RecipesContract {

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start();
        void stop();
        void queryRecipes();
    }

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showRecipes(@NonNull List<Recipe> recipes);
        void showMessage(@NonNull String message);
    }
}

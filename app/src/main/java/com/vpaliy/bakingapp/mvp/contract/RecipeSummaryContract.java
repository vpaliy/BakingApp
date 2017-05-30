package com.vpaliy.bakingapp.mvp.contract;

import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.BasePresenter;
import com.vpaliy.bakingapp.mvp.BaseView;
import android.support.annotation.NonNull;

public interface RecipeSummaryContract {

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
        void fetchById(int recipeId);
    }

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showRecipe(@NonNull Recipe recipe);
        void showMessage(@NonNull String message);
    }
}

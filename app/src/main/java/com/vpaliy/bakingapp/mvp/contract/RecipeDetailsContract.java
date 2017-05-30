package com.vpaliy.bakingapp.mvp.contract;

import com.vpaliy.bakingapp.mvp.BasePresenter;
import com.vpaliy.bakingapp.mvp.BaseView;
import android.support.annotation.NonNull;

public interface RecipeDetailsContract {

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
    }

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
    }
}

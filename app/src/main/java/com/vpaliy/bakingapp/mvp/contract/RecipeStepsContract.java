package com.vpaliy.bakingapp.mvp.contract;


import com.vpaliy.bakingapp.mvp.BasePresenter;
import com.vpaliy.bakingapp.mvp.BaseView;

import android.support.annotation.NonNull;

public interface RecipeStepsContract {
    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void stop();
        void showNext();
        void showPrev();
        void showCurrent();
    }

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showMessage(@NonNull String message);
        void playVideo(String videoUrl);
        void pauseVideo();
        void hideNextButton();
        void hidePrevButton();
        void showNextButton();
        void showPrevButton();
        void showPageNumber(int currentPage, int total);
        void hidePlayer();
        void showDescription(String shortDescription,String description);
    }
}

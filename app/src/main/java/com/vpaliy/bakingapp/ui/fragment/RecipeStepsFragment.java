package com.vpaliy.bakingapp.ui.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;

import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.Presenter;

public class RecipeStepsFragment extends Fragment
        implements RecipeStepsContract.View {

    private MediaSessionCompat mediaSession;


    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showDescription(String description) {

    }

    @Override
    public void showImage(String imageUrl) {

    }

    @Override
    public void playVideo(String videoUrl) {

    }

    @Override
    public void pauseVideo() {

    }

    @Override
    public void attachPresenter(@NonNull Presenter presenter) {

    }

    @Override
    public void hidePrevButton() {

    }

    @Override
    public void hideNextButton() {

    }

    @Override
    public void showNextButton() {

    }

    @Override
    public void showPrevButton() {

    }
}

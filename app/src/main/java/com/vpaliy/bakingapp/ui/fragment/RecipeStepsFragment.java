package com.vpaliy.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.media.IPlayback;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.Presenter;
import android.support.v4.media.session.PlaybackStateCompat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;


public class RecipeStepsFragment extends BaseFragment
        implements RecipeStepsContract.View,IPlayback.Callback {

    private static final String SESSION_TAG="media_session_log_tag";

    @Inject
    protected IPlayback playback;

    private Presenter presenter;

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playback.setCallback(this);
        mediaSession=new MediaSessionCompat(getContext(),SESSION_TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS|
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder=new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE|
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(playback.getMediaSessionCallback());

        MediaControllerCompat mediaController=new MediaControllerCompat(getContext(),mediaSession);
        MediaControllerCompat.setMediaController(getActivity(),mediaController);

    }

    @Override
    public void onMediaPlay() {
        mediaSession.setActive(true);
    }

    @Override
    public void onMediaStop() {
        mediaSession.setActive(false);
    }

    @Override
    public void onCompletion() {

    }

    @Override
    public void onStateChanged(int state) {

    }

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
        playback.play(videoUrl);
    }

    @Override
    public void pauseVideo() {
        playback.pause();
    }

    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
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

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(BakingApp.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}

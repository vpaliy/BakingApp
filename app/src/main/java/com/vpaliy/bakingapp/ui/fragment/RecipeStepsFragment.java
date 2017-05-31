package com.vpaliy.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.media.IPlayback;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.Presenter;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;


public class RecipeStepsFragment extends BaseFragment
        implements RecipeStepsContract.View,IPlayback.Callback {

    private static final String SESSION_TAG="media_session_log_tag";
    private static final String TAG=RecipeStepsFragment.class.getSimpleName();

    @BindView(R.id.player)
    protected SimpleExoPlayerView playerView;

    @BindView(R.id.step_short_description)
    protected TextView shortDescription;

    @BindView(R.id.step_description)
    protected TextView description;

    @BindView(R.id.pages)
    protected TextView pageTracker;

    @BindView(R.id.step_next)
    protected View next;

    @BindView(R.id.step_prev)
    protected View previous;

    @Inject
    protected IPlayback<?> playback;

    private Presenter presenter;

    private MediaSessionCompat mediaSession;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playback.setCallback(this);
        mediaSession=new MediaSessionCompat(getContext(),SESSION_TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS|
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        PlaybackStateCompat.Builder stateBuilder=new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE|
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(playback.getMediaSessionCallback());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_recipe_steps,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.showCurrent();
            next.setOnClickListener(v->{
                presenter.showNext();onStepChanged();
            });
            previous.setOnClickListener(v->{
                presenter.showPrev();onStepChanged();
            });
        }
    }

    private void onStepChanged(){
        if(playerView.getVisibility()==View.GONE){
            playerView.setVisibility(View.VISIBLE);
        }else{
            playback.stop();
            playerView.setPlayer(null);
            playerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMediaPlay() {
        mediaSession.setActive(true);
        if(playerView.getPlayer()==null){
            playerView.setVisibility(View.VISIBLE);
            playerView.setPlayer(SimpleExoPlayer.class.cast(playback.getPlayer()));
        }
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
    public void showPageNumber(int currentPage, int total) {
        String result=Integer.toString(currentPage)+'/'+Integer.toString(total);
        pageTracker.setText(result);
    }

    @Override
    public void showDescription(String shortDescription,String description) {
        this.shortDescription.setText(shortDescription);
        this.description.setText(description);
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
        previous.animate()
                .scaleY(0f)
                .scaleX(0f)
                .setDuration(150)
                .start();
    }

    @Override
    public void hideNextButton() {
        next.animate()
                .scaleY(0f)
                .scaleX(0f)
                .setDuration(150)
                .start();
    }

    @Override
    public void showNextButton() {
        next.animate()
                .scaleX(1f)
                .scaleY(1.f)
                .setDuration(150)
                .start();
    }

    @Override
    public void showPrevButton() {
        previous.animate()
                .scaleX(1f)
                .scaleY(1.f)
                .setDuration(150)
                .start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(BakingApp.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}

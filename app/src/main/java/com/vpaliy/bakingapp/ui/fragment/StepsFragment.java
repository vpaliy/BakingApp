package com.vpaliy.bakingapp.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.media.IPlayback;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.Presenter;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.bus.event.OnChangeToolbarEvent;
import com.vpaliy.bakingapp.ui.bus.event.OnChangeVisibilityEvent;
import com.vpaliy.bakingapp.utils.Permissions;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;


public class StepsFragment extends BaseFragment
        implements RecipeStepsContract.View, IPlayback.Callback{

    private static final String SESSION_TAG="media_session_log_tag";
    private static final String TAG=StepsFragment.class.getSimpleName();

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

    @Inject
    protected RxBus rxBus;

    @BindView(R.id.footer)
    protected View footer;

    @BindView(R.id.cardView)
    protected CardView cardView;

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
                             @Nullable ViewGroup parentContainer,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_recipe_steps,parentContainer,false);
        bind(root);
        return root;
    }

    private void updateSystemUI(){
        rxBus.send(OnChangeVisibilityEvent.change(isPortrait()));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.showCurrent();
        new Handler().post(this::updateSystemUI);
    }

    private boolean isPortrait(){
        return getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            next.setOnClickListener(v->
                 presenter.showNext());
            previous.setOnClickListener(v->
                 presenter.showPrev());
        }
    }


    @Override
    public void hidePlayer() {
        playback.stop();
        playerView.setPlayer(null);
        playerView.setVisibility(View.GONE);
    }

    @Override
    public void onMediaPlay() {
        mediaSession.setActive(true);
        playerView.setVisibility(View.VISIBLE);
        if(playerView.getPlayer()==null){
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
        result=getString(R.string.step)+":"+Integer.toString(currentPage);
        rxBus.send(OnChangeToolbarEvent.change(result));
    }

    @Override
    public void showDescription(String shortDescription,String description) {
        this.shortDescription.setText(shortDescription);
        this.description.setText(description);
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
        hideButton(previous);
    }

    private void hideButton(View view){
        view.animate()
                .alpha(0f)
                .setDuration(150)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    private void showButton(View view){
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(150)
                .setListener(null)
                .start();
    }
    @Override
    public void hideNextButton() {
        hideButton(next);
    }

    @Override
    public void showNextButton() {
        showButton(next);
    }

    @Override
    public void showPrevButton() {
       showButton(previous);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()");
        presenter.stop();
    }

    private ViewGroup getRoot(){
        return ViewGroup.class.cast(getView());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean isVisible=playerView.getVisibility()==View.VISIBLE;
        if(Permissions.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
            TransitionManager.beginDelayedTransition(getRoot());
        }
        if(!isPortrait()){
            if(isVisible){
                footer.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                playerView.getLayoutParams().width=ViewGroup.LayoutParams.MATCH_PARENT;
                playerView.getLayoutParams().height=ViewGroup.LayoutParams.MATCH_PARENT;
                updateSystemUI();
            }
        }else{
            if(isVisible){
                playerView.post(()->{
                    playerView.getLayoutParams().width=ViewGroup.LayoutParams.MATCH_PARENT;
                    playerView.getLayoutParams().height=(int)getResources().getDimension(R.dimen.player_height);
                });
                footer.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                updateSystemUI();
            }
        }
    }

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(BakingApp.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}

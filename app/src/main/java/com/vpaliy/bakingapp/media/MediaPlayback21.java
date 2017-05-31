package com.vpaliy.bakingapp.media;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

public class MediaPlayback21 implements IPlayback<ExoPlayer>,
        ExoPlayer.EventListener {

    private static final String TAG=MediaPlayback21.class.getSimpleName();

    private Context context;
    private Callback callback;
    private ExoPlayer player;
    private String currentSource;
    private MediaSessionCallback mediaSessionCallback;

    public MediaPlayback21(@NonNull Context context){
        this.context=context;
        this.mediaSessionCallback=new MediaSessionCallback(this);
    }

    @Override
    public void play(String source) {
        Log.d(TAG,"play:"+source);
        if(source!=null) {
            createPlayerIfNeeded();
            boolean hasChanged= TextUtils.equals(source,currentSource);
            if(!hasChanged) {
                this.currentSource=source;
                String userAgent = Util.getUserAgent(context, context.getApplicationInfo().name);
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(source), new DefaultDataSourceFactory(context,
                        userAgent), new DefaultExtractorsFactory(), null, null);
                player.prepare(mediaSource);
            }
            player.setPlayWhenReady(true);
            if (callback != null) {
                callback.onMediaPlay();
            }
        }
    }

    @Override
    public void pause() {
        Log.d(TAG,"pause()");
        if(player!=null){
            player.setPlayWhenReady(false);
            if(callback!=null){
                callback.onMediaStop();
            }
        }
    }

    @Override
    public void stop() {
        Log.d(TAG,"stop()");
        releasePlayer();
        if(callback!=null){
            callback.onMediaStop();
        }
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback=callback;
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onPositionDiscontinuity() {
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(callback!=null){
            callback.onStateChanged(convertState(playbackState,playWhenReady));
        }
    }

    private int convertState(int playbackState, boolean playWhenReady){
        switch (playbackState){
            case ExoPlayer.STATE_BUFFERING:
                return PlaybackStateCompat.STATE_BUFFERING;
            case ExoPlayer.STATE_READY:
                return playWhenReady?PlaybackStateCompat.STATE_PLAYING:PlaybackStateCompat.STATE_PAUSED;
            case ExoPlayer.STATE_ENDED:
                return PlaybackStateCompat.STATE_STOPPED;
            default:
                return PlaybackStateCompat.STATE_NONE;
        }
    }

    @Override
    public long getStreamingPosition() {
        if(player!=null){
            return player.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public boolean isPlaying() {
        return player!=null && player.getPlayWhenReady();
    }

    @Override
    public void seekTo(long position) {
        if(player!=null){
            player.seekTo(position);
        }
    }

    private void createPlayerIfNeeded(){
        Log.d(TAG,"create player");
        if(player==null){
            TransferListener<? super DataSource> bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory((BandwidthMeter) bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            player= ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            player.addListener(this);

        }
    }

    private void releasePlayer() {
        Log.d(TAG,"releasePlayer");
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public ExoPlayer getPlayer() {
        return player;
    }

    @Override
    public MediaSessionCallback getMediaSessionCallback() {
        return mediaSessionCallback;
    }

    @Override
    public String getResource() {
        return currentSource;
    }
}

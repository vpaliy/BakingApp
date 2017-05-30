package com.vpaliy.bakingapp.media;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.support.annotation.NonNull;

public class MediaPlayback implements IPlayback,
        AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnSeekCompleteListener {

    private MediaPlayer mediaPlayer;
    private Callback callback;
    private Context context;
    private boolean focusGained;
    private boolean receiverRegistered;
    private AudioManager audioManager;
    private MediaSessionCallback mediaSessionCallback;
    private volatile String currentSource;
    private volatile long currentPosition;
    private int state;

    private final IntentFilter audioBecomingNoisyIntent=
            new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

    private final BroadcastReceiver audioNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                if (isPlaying()) {

                }
            }
        }
    };

    public MediaPlayback(@NonNull Context context,
                         @NonNull AudioManager audioManager){
        this.context=context;
        this.audioManager=audioManager;
    }


    @Override
    public boolean isPlaying() {
        return focusGained && mediaPlayer!=null && mediaPlayer.isPlaying();
    }

    @Override
    public void play(String source) {
        requestFocus();
        boolean hasChanged = !TextUtils.equals(source, this.currentSource);
        if(hasChanged){
            this.currentPosition=0;
        }
        if(state== PlaybackStateCompat.STATE_PAUSED && mediaPlayer!=null){

        }
        if(callback!=null) callback.onMediaPlay();
        setUpPlayer();
        registerReceiver();
    }

    @Override
    public void stop() {
        releaseFocus();
        if(callback!=null) callback.onMediaStop();
        releasePlayer();
        unregisterReceiver();
    }

    @Override
    public MediaSessionCallback getMediaSessionCallback() {
        return mediaSessionCallback;
    }

    @Override
    public String getResource() {
        return currentSource;
    }

    @Override
    public void seekTo(long position) {

    }

    @Override
    public void pause() {
        unregisterReceiver();
    }

    @Override
    public void onSeekComplete(MediaPlayer player) {

    }

    @Override
    public void onCompletion(MediaPlayer player) {
        callback.onCompletion();
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        configMediaPlayer();
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback=callback;
    }


    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    private void setUpPlayer(){

    }

    private void requestFocus(){

    }


    private void releaseFocus(){

    }

    private void configMediaPlayer(){

    }

    private void releasePlayer(){
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    private void registerReceiver(){
        if(!receiverRegistered){
            context.registerReceiver(audioNoisyReceiver,audioBecomingNoisyIntent);
            receiverRegistered=true;
        }
    }

    private void unregisterReceiver(){
        if(receiverRegistered){
            context.unregisterReceiver(audioNoisyReceiver);
            receiverRegistered=false;
        }
    }

}

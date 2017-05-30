package com.vpaliy.bakingapp.media;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.annotation.NonNull;

public class MediaPlayback implements IPlayback,
        AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener{

    private MediaPlayer mediaPlayer;
    private Callback callback;
    private Context context;
    private boolean focusGained;
    private boolean receiverRegistered;
    private AudioManager audioManager;
    private MediaSessionCallback mediaSessionCallback;
    private volatile String currentSource;
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

    public MediaPlayback(@NonNull Context context){
        this.context=context;
    }


    @Override
    public boolean isPlaying() {
        return mediaPlayer!=null && mediaPlayer.isPlaying();
    }

    @Override
    public void play(String source) {

        requestFocus();
        if(callback!=null) callback.onPlay();
        setUpPlayer();
        registerReceiver();
    }

    @Override
    public void stop() {
        releaseFocus();
        if(callback!=null) callback.onStop();
        releasePlayer();
        unregisterReceiver();
    }

    @Override
    public void seekTo(long position) {

    }

    @Override
    public void pause() {
        unregisterReceiver();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void setCallback(Callback callback) {
        this.callback=callback;
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            play(currentSource);
        }

        @Override
        public void onPause() {
            super.onPause();
            pause();
        }

        @Override
        public void onStop() {
            super.onStop();
            stop();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            seekTo(pos);
        }
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

package com.vpaliy.bakingapp.media;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;

public class MediaPlayback21 implements IPlayback {

    private Context context;
    private AudioManager audioManager;
    private int state;

    public MediaPlayback21(@NonNull Context context, AudioManager audioManager){
        this.audioManager=audioManager;
        this.context=context;

    }

    @Override
    public void play(String source) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setCallback(Callback callback) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void seekTo(long position) {

    }

    @Override
    public MediaSessionCallback getMediaSessionCallback() {
        return null;
    }

    @Override
    public String getResource() {
        return null;
    }
}

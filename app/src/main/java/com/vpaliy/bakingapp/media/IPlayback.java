package com.vpaliy.bakingapp.media;


public interface IPlayback {
    void play(String source);
    void seekTo(long position);
    void setCallback(Callback callback);
    void pause();
    void stop();
    boolean isPlaying();

    interface Callback {
        void onPlay();
        void onStop();
        void onCompletetion();
        void onStateChanged(int state);
    }
}

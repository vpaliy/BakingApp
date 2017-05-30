package com.vpaliy.bakingapp.media;


public interface IPlayback {
    void play(String source);
    void seekTo(long position);
    void setCallback(Callback callback);
    void pause();
    void stop();
    boolean isPlaying();
    String getResource();
    MediaSessionCallback getMediaSessionCallback();

    interface Callback {
        void onMediaPlay();
        void onMediaStop();
        void onCompletion();
        void onStateChanged(int state);
    }


}

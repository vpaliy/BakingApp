package com.vpaliy.bakingapp.media;


public interface IPlayback<T> {
    void play(String source);
    void seekTo(long position);
    void setCallback(Callback callback);
    void pause();
    void stop();
    boolean isPlaying();
    long getStreamingPosition();
    String getResource();
    MediaSessionCallback getMediaSessionCallback();
    T getPlayer();

    interface Callback {
        void onMediaPlay();
        void onMediaStop();
        void onCompletion();
        void onStateChanged(int state);
    }


}

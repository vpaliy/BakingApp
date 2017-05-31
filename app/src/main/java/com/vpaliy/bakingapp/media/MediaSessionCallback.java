package com.vpaliy.bakingapp.media;

import android.support.v4.media.session.MediaSessionCompat;

import com.vpaliy.bakingapp.di.scope.ViewScope;
import javax.inject.Inject;

@ViewScope
public class MediaSessionCallback extends MediaSessionCompat.Callback {

    private final IPlayback<?> playback;

    @Inject
    public MediaSessionCallback(IPlayback<?> playback){
        this.playback=playback;

    }

    @Override
    public void onPlay() {
        super.onPlay();
        playback.play(playback.getResource());
    }

    @Override
    public void onPause() {
        super.onPause();
        playback.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        playback.stop();
    }

    @Override
    public void onSeekTo(long pos) {
        super.onSeekTo(pos);
        playback.seekTo(pos);
    }


}

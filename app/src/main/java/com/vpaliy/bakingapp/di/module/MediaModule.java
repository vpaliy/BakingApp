package com.vpaliy.bakingapp.di.module;

import android.content.Context;
import com.vpaliy.bakingapp.media.IPlayback;
import com.vpaliy.bakingapp.media.MediaPlayback;
import com.vpaliy.bakingapp.media.MediaPlayback21;
import android.media.AudioManager;
import android.os.Build;

import com.vpaliy.bakingapp.di.scope.ViewScope;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class MediaModule {

    @ViewScope
    @Provides
    IPlayback<?> providePlayback(@NonNull Context context){
        AudioManager audioManager=AudioManager.class.cast(context.getSystemService(Context.AUDIO_SERVICE));
        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT){
            return new MediaPlayback21(context);
        }
        return new MediaPlayback(context,audioManager);
    }
}

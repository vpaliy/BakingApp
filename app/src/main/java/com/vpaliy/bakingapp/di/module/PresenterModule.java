package com.vpaliy.bakingapp.di.module;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import com.vpaliy.bakingapp.media.IPlayback;
import com.vpaliy.bakingapp.media.MediaPlayback;
import com.vpaliy.bakingapp.media.MediaPlayback21;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import com.vpaliy.bakingapp.mvp.presenter.SummaryPresenter;
import com.vpaliy.bakingapp.mvp.presenter.RecipesPresenter;

import com.vpaliy.bakingapp.di.scope.ViewScope;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {


    @ViewScope
    @Provides
    RecipesContract.Presenter recipesPresenter(RecipesPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    RecipeSummaryContract.Presenter recipeDetailsPresenter(SummaryPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    IPlayback<?> providePlayback(Context context){
        AudioManager audioManager=AudioManager.class.cast(context.getSystemService(Context.AUDIO_SERVICE));
        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT){
            return new MediaPlayback21(context);
        }
        return new MediaPlayback(context,audioManager);
    }

}

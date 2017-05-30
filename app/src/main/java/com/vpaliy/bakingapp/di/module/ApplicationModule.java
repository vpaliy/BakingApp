package com.vpaliy.bakingapp.di.module;

import android.app.Application;
import android.content.Context;

import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.utils.messenger.Messenger;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import com.vpaliy.bakingapp.utils.scheduler.SchedulerProvider;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application=application;
    }

    @Singleton
    @Provides
    Context provideWithContext(){
        return application;
    }

    @Singleton
    @Provides
    BaseSchedulerProvider provideScheduler(){
        return new SchedulerProvider();
    }

    @Singleton
    @Provides
    MessageProvider provideMessenger(Messenger messenger){
        return messenger;
    }

    @Singleton
    @Provides
    RxBus provideBus(){
        return new RxBus();
    }
}

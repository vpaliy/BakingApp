package com.vpaliy.bakingapp.utils.scheduler;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @NonNull
    public Scheduler multi(){
        return Schedulers.immediate();
    }

    @NonNull
    public Scheduler ui() {
        return Schedulers.immediate();
    }
}

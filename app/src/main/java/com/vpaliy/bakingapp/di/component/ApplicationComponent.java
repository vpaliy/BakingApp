package com.vpaliy.bakingapp.di.component;

import com.vpaliy.bakingapp.di.module.DataModule;
import com.vpaliy.bakingapp.di.module.NetworkModule;

import dagger.Component;

@Component(modules = {DataModule.class, NetworkModule.class})
public interface ApplicationComponent {

}

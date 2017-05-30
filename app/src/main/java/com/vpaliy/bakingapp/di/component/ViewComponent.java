package com.vpaliy.bakingapp.di.component;

import com.vpaliy.bakingapp.di.module.PresenterModule;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class,
        modules = {PresenterModule.class})
public interface ViewComponent {
}

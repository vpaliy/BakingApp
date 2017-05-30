package com.vpaliy.bakingapp.di.component;

import com.vpaliy.bakingapp.data.remote.RecipeAPI;
import com.vpaliy.bakingapp.di.module.ApplicationModule;
import com.vpaliy.bakingapp.di.module.DataModule;
import com.vpaliy.bakingapp.di.module.NetworkModule;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.ui.activity.BaseActivity;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        DataModule.class,
        NetworkModule.class,
        ApplicationModule.class
})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
    RecipeAPI recipeAPI();
    IRepository<Recipe> repository();
    BaseSchedulerProvider schedulerProvider();
    MessageProvider messageProvider();
}

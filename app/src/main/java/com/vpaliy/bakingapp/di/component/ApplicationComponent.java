package com.vpaliy.bakingapp.di.component;

import android.content.Context;
import com.vpaliy.bakingapp.di.module.ApplicationModule;
import com.vpaliy.bakingapp.di.module.DataModule;
import com.vpaliy.bakingapp.di.module.NetworkModule;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.ui.activity.BaseActivity;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import com.vpaliy.bakingapp.widget.RecipeWidget;
import com.vpaliy.bakingapp.widget.RecipeWidgetService;

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
    void inject(RecipeWidget widget);
    void inject(RecipeWidgetService widgetService);
    IRepository<Recipe> repository();
    BaseSchedulerProvider schedulerProvider();
    MessageProvider messageProvider();
    RxBus bus();
    Context context();
}

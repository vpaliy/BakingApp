package di;


import com.vpaliy.bakingapp.di.component.ApplicationComponent;
import com.vpaliy.bakingapp.di.module.ApplicationModule;
import com.vpaliy.bakingapp.di.module.NetworkModule;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        MockDataModule.class,
        NetworkModule.class,
        ApplicationModule.class
})
public interface MockApplicationComponent extends ApplicationComponent {
    IRepository<Recipe> repository();
    BaseSchedulerProvider schedulerProvider();
}

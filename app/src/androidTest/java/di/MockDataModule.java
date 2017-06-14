package di;

import com.vpaliy.bakingapp.data.RecipeRepository;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import com.vpaliy.bakingapp.utils.scheduler.ImmediateSchedulerProvider;
import com.vpaliy.bakingapp.utils.scheduler.SchedulerProvider;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockDataModule {

    @Singleton
    @Provides
    IRepository<Recipe> recipeRepository(){
        return Mockito.mock(RecipeRepository.class);
    }

    @Singleton
    @Provides
    BaseSchedulerProvider baseSchedulerProvider(){
        return new ImmediateSchedulerProvider();
    }
}

package com.vpaliy.bakingapp.di.component;

import com.vpaliy.bakingapp.di.module.MediaModule;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.di.scope.ViewScope;
import com.vpaliy.bakingapp.ui.fragment.RecipeStepsFragment;
import com.vpaliy.bakingapp.ui.fragment.RecipeSummaryFragment;
import com.vpaliy.bakingapp.ui.fragment.RecipesFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class,
        modules = {PresenterModule.class})
public interface ViewComponent {
    void inject(RecipesFragment fragment);
    void inject(RecipeSummaryFragment fragment);
    void inject(RecipeStepsFragment fragment);
}

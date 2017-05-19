package com.vpaliy.bakingapp.di.module;

import com.vpaliy.bakingapp.data.mapper.IngredientMapper;
import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.mapper.RecipeMapper;
import com.vpaliy.bakingapp.data.mapper.StepMapper;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton
    @Provides
    Mapper<Ingredient,IngredientEntity> ingredientMapper(IngredientMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<Recipe,RecipeEntity> recipeMapper(RecipeMapper mapper){
        return mapper;
    }

    @Singleton
    @Provides
    Mapper<Step,StepEntity> stepMapper(StepMapper mapper){
        return mapper;
    }
}

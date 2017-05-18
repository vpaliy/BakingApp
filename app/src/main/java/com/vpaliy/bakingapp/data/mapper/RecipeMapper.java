package com.vpaliy.bakingapp.data.mapper;

import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.model.Recipe;


public class RecipeMapper implements Mapper<Recipe,RecipeEntity> {

    @Override
    public Recipe map(RecipeEntity recipeEntity) {
        return null;
    }

    @Override
    public RecipeEntity reverseMap(Recipe recipe) {
        return null;
    }
}

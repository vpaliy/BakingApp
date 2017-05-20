package com.vpaliy.bakingapp.data.mapper;

import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeMapper implements Mapper<Recipe,RecipeEntity> {

    private IngredientMapper ingredientMapper;
    private StepMapper stepMapper;

    @Inject
    public RecipeMapper(@NonNull IngredientMapper ingredientMapper,
                        @NonNull StepMapper stepMapper){
        this.ingredientMapper=ingredientMapper;
        this.stepMapper=stepMapper;
    }


    @Override
    public Recipe map(RecipeEntity recipeEntity) {
        Recipe recipe=new Recipe();
        recipe.setId(recipeEntity.getId());
        recipe.setImageUrl(recipeEntity.getImageUrl());
        recipe.setName(recipe.getName());
        recipe.setServings(recipeEntity.getServings());
        if(recipeEntity.getIngredients()!=null) {
            List<Ingredient> ingredientList = new ArrayList<>(recipeEntity.getIngredients().size());
            recipeEntity.getIngredients().forEach(ingredientEntity ->
                    ingredientList.add(ingredientMapper.map(ingredientEntity)));
            recipe.setIngredients(ingredientList);
        }
        if(recipeEntity.getSteps()!=null){
            List<Step> stepList=new ArrayList<>(recipeEntity.getSteps().size());
            recipeEntity.getSteps().forEach(step->stepList.add(stepMapper.map(step)));
            recipe.setSteps(stepList);
        }
        return recipe;
    }

    @Override
    public RecipeEntity reverseMap(Recipe recipe) {
        RecipeEntity entity=new RecipeEntity();
        entity.setId(recipe.getId());
        entity.setName(recipe.getName());
        entity.setServings(recipe.getServings());
        entity.setImageUrl(recipe.getImageUrl());
        if(recipe.getSteps()!=null){
            List<StepEntity> stepList=new ArrayList<>(recipe.getSteps().size());
            recipe.getSteps().forEach(step->stepList.add(stepMapper.reverseMap(step)));
            entity.setSteps(stepList);
        }

        if(recipe.getIngredients()!=null){
            List<IngredientEntity> ingredientList=new ArrayList<>(recipe.getIngredients().size());
            recipe.getIngredients().forEach(ingredient ->ingredientList.add(ingredientMapper.reverseMap(ingredient)));
            entity.setIngredients(ingredientList);
        }
        return entity;
    }

    @Override
    public List<Recipe> map(List<RecipeEntity> recipeEntities) {
        if(recipeEntities==null||recipeEntities.isEmpty()) return null;
        List<Recipe> result=new ArrayList<>(recipeEntities.size());
        recipeEntities.forEach(recipeEntity -> result.add(map(recipeEntity)));
        return result;
    }
}

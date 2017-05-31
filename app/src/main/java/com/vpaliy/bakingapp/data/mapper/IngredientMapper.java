package com.vpaliy.bakingapp.data.mapper;

import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.domain.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IngredientMapper implements Mapper<Ingredient,IngredientEntity> {

    @Inject
    public IngredientMapper(){}

    @Override
    public Ingredient map(IngredientEntity ingredientEntity) {
        Ingredient ingredient=new Ingredient();
        ingredient.setId(ingredientEntity.getId());
        ingredient.setIngredient(ingredientEntity.getIngredient());
        ingredient.setMeasure(ingredientEntity.getMeasure());
        ingredient.setQuantity(ingredientEntity.getQuantity());
        return ingredient;
    }

    @Override
    public IngredientEntity reverseMap(Ingredient ingredient) {
        IngredientEntity entity=new IngredientEntity();
        entity.setId(ingredient.getId());
        entity.setQuantity(ingredient.getQuantity());
        entity.setMeasure(ingredient.getMeasure());
        entity.setIngredient(ingredient.getIngredient());
        return entity;
    }

    @Override
    public List<Ingredient> map(List<IngredientEntity> ingredientEntities) {
        if(ingredientEntities==null||ingredientEntities.isEmpty()) return null;
        List<Ingredient> result=new ArrayList<>(ingredientEntities.size());
        ingredientEntities.forEach(ingredientEntity -> result.add(map(ingredientEntity)));
        return result;
    }
}

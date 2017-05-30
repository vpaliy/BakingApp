package com.vpaliy.bakingapp.ui.bus.event;

public class OnRecipeClickEvent {

    public final int recipeId;

    private OnRecipeClickEvent(int recipeId){
        this.recipeId=recipeId;
    }

    public static OnRecipeClickEvent click(int recipeId){
        return new OnRecipeClickEvent(recipeId);
    }
}

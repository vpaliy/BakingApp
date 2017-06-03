package com.vpaliy.bakingapp.data.local;

import static com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import static com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper.Tables;

public enum RecipeMatchEnum {

    RECIPES(100, Recipes.CONTENT_DIR_TYPE,Tables.RECIPES,RecipeContract.PATH_RECIPE),
    RECIPE_ID(101,Recipes.CONTENT_ITEM_TYPE,null, RecipeContract.PATH_RECIPE+"/#"),
    RECIPE_INGREDIENTS_ID(103,Recipes.CONTENT_ITEM_TYPE,Tables.RECIPES_INGREDIENTS,RecipeContract.PATH_RECIPE+"/#/"+RecipeContract.PATH_INGREDIENT),
    RECIPE_STEP_ID(105,Recipes.CONTENT_ITEM_TYPE,null,RecipeContract.PATH_RECIPE+"/#/"+RecipeContract.PATH_STEP),

    STEPS(200,Steps.CONTENT_DIR_TYPE,Tables.STEPS,RecipeContract.PATH_STEP),
    STEP_ID(201,Steps.CONTENT_ITEM_TYPE,null,RecipeContract.PATH_STEP+"/#"),

    INGREDIENTS(300,Ingredients.CONTENT_DIR_TYPE,Tables.INGREDIENTS,RecipeContract.PATH_INGREDIENT),
    INGREDIENT_ID(301,Ingredients.CONTENT_ITEM_TYPE,null,RecipeContract.PATH_INGREDIENT+"/#"),
    INGREDIENT_RECIPES_ID(302,Ingredients.CONTENT_ITEM_TYPE,Tables.RECIPES_INGREDIENTS,RecipeContract.PATH_INGREDIENT+"/#/"+RecipeContract.PATH_RECIPE);

    public int code;
    public String contentType;
    public String table;
    public String path;

    RecipeMatchEnum(int code, String contentType,String table,String path){
        this.code=code;
        this.contentType=contentType;
        this.table=table;
        this.path=path;
    }
}

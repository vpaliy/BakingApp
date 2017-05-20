package com.vpaliy.bakingapp.data.local;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.vpaliy.bakingapp.data.local.RecipeDatabase.Tables;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Steps;

public class RecipeProvider extends ContentProvider {

    private RecipeUriMatcher uriMatcher;
    private RecipeDatabase database;


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return false;
    }


    public SqlQueryBuilder buildQuery(Uri uri, RecipeMatchEnum matchEnum){
        SqlQueryBuilder builder=new SqlQueryBuilder();
        String id;
        switch (matchEnum){
            case RECIPES:
                return builder.table(Tables.RECIPES);
            case INGREDIENTS:
                return builder.table(Tables.INGREDIENTS);
            case STEPS:
                return builder.table(Tables.STEPS);
            case RECIPE_ID:
                id= Recipes.getRecipeId(uri);
                return builder.table(Tables.RECIPES)
                        .where(Recipes.RECIPE_ID+"=?",id);
            case INGREDIENT_ID:
                id=Ingredients.getIngredientId(uri);
                return builder.table(Tables.INGREDIENTS)
                        .where(Ingredients.INGREDIENT_ID+"=?",id);
            case RECIPE_INGREDIENTS_ID:
                id=Recipes.getRecipeId(uri);
                return builder.table(Tables.RECIPE_JOIN_INGREDIENTS)
                        .mapToTable(Recipes.RECIPE_ID,Tables.RECIPES)
                        .mapToTable(Ingredients.INGREDIENT_ID,Tables.INGREDIENTS)
                        .where(Recipes.RECIPE_ID+"=?",id);
            case RECIPE_STEP_ID:
                id=Recipes.getRecipeId(uri);
                return builder.table(Tables.RECIPE_JOIN_STEPS)
                        .mapToTable(Recipes.RECIPE_ID,Tables.RECIPES)
                        .mapToTable(Steps.STEP_ID,Tables.STEPS)
                        .where(Steps.STEP_ID+"=?",id);
            case INGREDIENT_RECIPES_ID:
                id=Ingredients.getIngredientId(uri);
                return builder.table(Tables.INGREDIENTS_JOIN_RECIPE)
                        .mapToTable(Ingredients.INGREDIENT_ID,Tables.INGREDIENTS)
                        .mapToTable(Recipes.RECIPE_ID,Tables.RECIPES)
                        .where(Ingredients.INGREDIENT_ID+"=?",id);
            default:
                throw new UnsupportedOperationException("Unknown uri:"+uri);

        }
    }
}

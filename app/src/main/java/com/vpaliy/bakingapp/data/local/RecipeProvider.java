package com.vpaliy.bakingapp.data.local;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import static com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper.Tables;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vpaliy.bakingapp.domain.model.Recipe;


public class RecipeProvider extends ContentProvider {

    private RecipeUriMatcher uriMatcher;
    private RecipeDatabaseHelper database;


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db=database.getReadableDatabase();
        final RecipeMatchEnum recipeMatchEnum=uriMatcher.match(uri);

        SqlQueryBuilder builder=buildQuery(uri,recipeMatchEnum);
        Cursor cursor=builder
                .where(selection,selectionArgs)
                .query(db,projection,sortOrder);

        Context context = getContext();
        if (null != context) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return uriMatcher.getType(uri);
    }

    private void notifyChange(Uri uri) {
        if (getContext()!=null) {
            Context context = getContext();
            context.getContentResolver().notifyChange(uri, null);
        }
    }

    private void deleteDatabase(){
        database.close();
        Context context=getContext();
        if(context!=null){
            RecipeDatabaseHelper.deleteDatabase(context);
            database=new RecipeDatabaseHelper(context);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uri == RecipeContract.BASE_CONTENT_URI) {
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }
        final SQLiteDatabase db=database.getWritableDatabase();
        final RecipeMatchEnum recipeMatchEnum=uriMatcher.match(uri);
        final SqlQueryBuilder builder=buildQuery(uri,recipeMatchEnum);

        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db=database.getWritableDatabase();
        final RecipeMatchEnum recipeMatchEnum=uriMatcher.match(uri);
        final SqlQueryBuilder builder=buildQuery(uri,recipeMatchEnum);

        int retVal=builder
                .where(selection,selectionArgs)
                .update(db,values);
        notifyChange(uri);
        return retVal;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if(values==null) throw new IllegalArgumentException("Values are null");
        final SQLiteDatabase db=database.getWritableDatabase();
        final RecipeMatchEnum recipeMatchEnum=uriMatcher.match(uri);
        if(recipeMatchEnum.table!=null){
            db.insertWithOnConflict(recipeMatchEnum.table,null,values,SQLiteDatabase.CONFLICT_REPLACE);
            notifyChange(uri);
        }
        switch (recipeMatchEnum){
            case RECIPES:
                return Recipes.buildRecipeUri(values.getAsString(Recipes.RECIPE_ID));
            case RECIPE_STEP_ID:
                return Recipes.buildRecipeWithStepsUri(values.getAsString(Recipes.RECIPE_ID));
            case RECIPE_INGREDIENTS_ID:
                return Recipes.buildRecipeWithIngredientsUri(values.getAsString(Recipes.RECIPE_ID));
            case INGREDIENTS:
                return Ingredients.buildIngredientUri(values.getAsString(Ingredients.INGREDIENT_ID));
            case INGREDIENT_RECIPES_ID:
                return Ingredients.buildIngredientWithRecipesUri(values.getAsString(Ingredients.INGREDIENT_ID));
            case STEPS:
                return Steps.buildStepUri(values.getAsString(Steps.STEP_ID));
            default:
                throw new UnsupportedOperationException("Unknown URI"+uri);
        }
    }

    @Override
    public boolean onCreate() {
        if(getContext()!=null) {
            this.database = new RecipeDatabaseHelper(getContext());
            this.uriMatcher=new RecipeUriMatcher();
        }
        return database!=null;
    }


    private SqlQueryBuilder buildQuery(Uri uri, RecipeMatchEnum matchEnum){
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
                        .where(Recipes.RECIPE_ID+"=?",id);
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

package com.vpaliy.bakingapp.data.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import static com.vpaliy.bakingapp.data.local.RecipeContract.Steps;

public class RecipeDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="recipes.db";
    private static final int DATABASE_VERSION=8;

    public interface Tables {

        String RECIPES="recipes";

        String INGREDIENTS="ingredients";

        String STEPS="steps";

        String RECIPES_INGREDIENTS="recipes_ingredients";

        String RECIPE_JOIN_STEPS="recipes "+
                "INNER JOIN steps ON recipes.recipe_id=steps.ref_recipe_id";

        String RECIPE_JOIN_INGREDIENTS="recipes "+
                "INNER JOIN recipes_ingredients ON recipes.recipe_id=recipes_ingredients.ref_recipe_id";

        String INGREDIENTS_JOIN_RECIPE="ingredients "+
                "LEFT OUTER JOIN recipes_ingredients ON ingredients.ingredient_id=recipes_ingredients.ref_ingredient_id";
    }

    interface References {
        String RECIPE_ID="REFERENCES "+Tables.RECIPES+"("+ Recipes.RECIPE_ID+")";
        String INGREDIENT_ID="REFERENCES "+Tables.INGREDIENTS+"("+ Ingredients.INGREDIENT_ID+")";
    }

    public interface RecipesIngredients {
        String RECIPE_ID="ref_recipe_id";
        String INGREDIENT_ID="ref_ingredient_id";
    }

    public RecipeDatabaseHelper(@NonNull Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Tables.RECIPES+" ("+
                Recipes.RECIPE_ID+" INTEGER PRIMARY KEY NOT NULL,"+
                Recipes.RECIPE_NAME+" TEXT NOT NULL,"+
                Recipes.RECIPE_IMAGE_URL+" TEXT NOT NULL,"+
                Recipes.RECIPE_SERVINGS+" INTEGER NOT NULL,"+
                " UNIQUE (" + Recipes.RECIPE_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.STEPS+" ("+
                Steps.STEP_ID+" INTEGER PRIMARY KEY NOT NULL,"+
                Steps.STEP_DESCRIPTION+" TEXT,"+
                Steps.STEP_SHORT_DESCRIPTION+" TEXT,"+
                Steps.STEP_IMAGE_URL+" TEXT,"+
                Steps.STEP_RECIPE_ID+" INTEGER NOT NULL "+References.RECIPE_ID+","+
                Steps.STEP_VIDEO_URL+" TEXT,"+
                " UNIQUE (" + Steps.STEP_RECIPE_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.INGREDIENTS+" ("+
                Ingredients.INGREDIENT_ID+" INTEGER PRIMARY KEY NOT NULL,"+
                Ingredients.INGREDIENT_NAME+" TEXT,"+
                Ingredients.INGREDIENT_MEASURE+" TEXT,"+
                Ingredients.INGREDIENT_QUANTITY+" REAL,"+
                " UNIQUE (" + Ingredients.INGREDIENT_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE "+Tables.RECIPES_INGREDIENTS+" ("+
                BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                RecipesIngredients.RECIPE_ID+" INTEGER NOT NULL "+References.RECIPE_ID+","+
                RecipesIngredients.INGREDIENT_ID+" INTEGER NOT NULL "+References.INGREDIENT_ID+","+
                 " UNIQUE (" + RecipesIngredients.RECIPE_ID + "," + RecipesIngredients.INGREDIENT_ID + ") ON CONFLICT REPLACE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Tables.INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.STEPS);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.RECIPES);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.RECIPES_INGREDIENTS);
        onCreate(db);
    }


    static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}

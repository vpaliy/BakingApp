package com.vpaliy.bakingapp.data.local;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

public final class RecipeContract {

    interface RecipeColumns {
        String RECIPE_ID="recipe_id";
        String RECIPE_NAME="recipe_name";
        String RECIPE_IMAGE_URL="recipe_image_url";
        String RECIPE_SERVINGS="recipe_servings";

        String[] COLUMNS={RECIPE_ID,RECIPE_NAME,
                RECIPE_IMAGE_URL,RECIPE_SERVINGS
        };
    }

    interface StepColumns {
        String STEP_ID="step_id";
        String STEP_SHORT_DESCRIPTION="step_short_description";
        String STEP_DESCRIPTION="step_description";
        String STEP_VIDEO_URL="step_video_url";
        String STEP_IMAGE_URL="step_image_url";
        String STEP_RECIPE_ID="ref_recipe_id";

        String[] COLUMNS={STEP_ID,STEP_SHORT_DESCRIPTION,STEP_DESCRIPTION,
                STEP_VIDEO_URL,STEP_IMAGE_URL,STEP_RECIPE_ID
        };
    }

    interface IngredientColumns {
        String INGREDIENT_ID="ingredient_id";
        String INGREDIENT_MEASURE="ingredient_measure";
        String INGREDIENT_NAME="ingredient_name";
        String INGREDIENT_QUANTITY="ingredient_quantity";

        String[] COLUMNS={INGREDIENT_ID,INGREDIENT_MEASURE,
                INGREDIENT_NAME,INGREDIENT_QUANTITY
        };
    }

    public static final String CONTENT_AUTHORITY="com.vpaliy.bakingapp";
    public static final String PREFIX="content://";

    public static final Uri BASE_CONTENT_URI= Uri.parse(PREFIX+CONTENT_AUTHORITY);

    public static final String PATH_RECIPE="recipe";

    public static final String PATH_STEP="steps";

    public static final String PATH_INGREDIENT="ingredient";

    private RecipeContract(){
        throw new UnsupportedOperationException();
    }

    public static class Recipes implements RecipeColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_RECIPE;

        public static Uri buildRecipeUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static Uri buildRecipeWithStepsUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_STEP).build();
        }

        public static Uri buildRecipeWithIngredientsUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_INGREDIENT).build();
        }

        public static String getRecipeId(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }


    public static class Steps implements StepColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEP;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_STEP;

        public static Uri buildStepUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

    public static class Ingredients implements IngredientColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +PATH_INGREDIENT;

        public static Uri buildIngredientWithRecipesUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).appendPath(PATH_RECIPE).build();
        }

        public static Uri buildIngredientUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getIngredientId(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }
}

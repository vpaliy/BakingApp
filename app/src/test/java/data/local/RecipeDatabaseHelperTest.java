package data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.compat.BuildConfig;
import com.vpaliy.bakingapp.data.local.DatabaseUtils;
import com.vpaliy.bakingapp.data.local.RecipeContract;
import com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper.Tables;
import com.vpaliy.bakingapp.data.model.StepEntity;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import data.RecipeTestUtils;

import static data.RecipeTestUtils.FAKER_ID;
import static data.RecipeTestUtils.FAKE_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import org.robolectric.annotation.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest = Config.NONE,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class RecipeDatabaseHelperTest {

    private RecipeDatabaseHelper recipeDatabaseHelper;

    @Before
    public void setUp(){
        Context context= RuntimeEnvironment.application;
        recipeDatabaseHelper=new RecipeDatabaseHelper(context);
    }

    @After
    public void cleanUp(){
        if(recipeDatabaseHelper!=null) recipeDatabaseHelper.close();
    }

    @Test
    public void returnsCorrectDatabaseName(){
        String databaseName=RecipeDatabaseHelper.DATABASE_NAME;
        assertThat(databaseName,is(recipeDatabaseHelper.getDatabaseName()));
    }

    /**
     *  Test inserts into the database
     */

    @Test
    public void insertsRecipeEntityIntoDatabase(){
        RecipeEntity entity= RecipeTestUtils.provideRecipeEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.RECIPES,null, DatabaseUtils.toValues(entity));

        Cursor cursor=db.query(Tables.RECIPES,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertTrue(cursor.moveToNext());
        cursor.close();
    }

    @Test
    public void insertsIngredientEntityIntoDatabase(){
        IngredientEntity entity=RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.INGREDIENTS,null,DatabaseUtils.toValues(entity));

        Cursor cursor=db.query(Tables.INGREDIENTS,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertTrue(cursor.moveToNext());
        assertThat(cursor.getCount(),is(1));
        cursor.close();
    }

    @Test
    public void insertsStepEntityIntoDatabase(){
        StepEntity entity=RecipeTestUtils.provideStepEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.STEPS,null,DatabaseUtils.toValues(entity, FAKE_ID));

        Cursor cursor=db.query(Tables.STEPS,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertTrue(cursor.moveToNext());
        assertThat(cursor.getCount(),is(1));
        cursor.close();
    }

    @Test
    public void insertsRecipesAndIngredientsIntoJunctionTable(){
        RecipeEntity recipeEntity=RecipeTestUtils.provideRecipeEntity();
        IngredientEntity ingredientEntity=RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.RECIPES_INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity,recipeEntity));

        Cursor cursor=db.query(Tables.RECIPES_INGREDIENTS,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertTrue(cursor.moveToNext());
        assertThat(cursor.getCount(),is(1));
        cursor.close();
    }

    @Test(expected = SQLiteConstraintException.class)
    public void failsIfInsertIngredientWithSameId(){
        IngredientEntity ingredientEntity= RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insertOrThrow(Tables.INGREDIENTS,null, DatabaseUtils.toValues(ingredientEntity));
        db.insertOrThrow(Tables.INGREDIENTS,null, DatabaseUtils.toValues(ingredientEntity));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void failsIfInsertRecipeWithSameId(){
        RecipeEntity recipeEntity=RecipeTestUtils.provideRecipeEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insertOrThrow(Tables.RECIPES,null,DatabaseUtils.toValues(recipeEntity));
        db.insertOrThrow(Tables.RECIPES,null,DatabaseUtils.toValues(recipeEntity));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void failsIfInsertStepThatHasNotUniqueRecipeId(){
        StepEntity stepEntity=RecipeTestUtils.provideStepEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insertOrThrow(Tables.STEPS,null,DatabaseUtils.toValues(stepEntity,FAKE_ID));
        db.insertOrThrow(Tables.STEPS,null,DatabaseUtils.toValues(stepEntity,FAKE_ID));

    }

    @Test
    public void failsIfInsertRecipeAndIngredientWithSameId(){
        RecipeEntity recipeEntity=RecipeTestUtils.provideRecipeEntity();
        IngredientEntity ingredientEntity=RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insertOrThrow(Tables.RECIPES_INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity,recipeEntity));
        db.insertOrThrow(Tables.RECIPES_INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity,recipeEntity));
    }

    /**
     * Test query methods
     */

    @Test
    public void queryAllRecipesFromDatabase(){
        RecipeEntity entity=RecipeTestUtils.provideRecipeEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.RECIPES,null,DatabaseUtils.toValues(entity));
        entity.setId(FAKER_ID);
        db.insert(Tables.RECIPES,null,DatabaseUtils.toValues(entity));

        Cursor cursor=db.query(Tables.RECIPES,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(),is(2));

        while(cursor.moveToNext()){
            RecipeEntity result=DatabaseUtils.toRecipe(cursor);
            assertThat(result,notNullValue());
            assertTrue(result.getId()==FAKER_ID||result.getId()==FAKE_ID);
            assertThat(result.getName(),is(entity.getName()));
            assertThat(result.getImageUrl(),is(entity.getImageUrl()));
            assertThat(result.getServings(),is(entity.getServings()));
        }
        cursor.close();
    }

    @Test
    public void queryAllIngredientsFromDatabase(){
        IngredientEntity entity=RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.INGREDIENTS,null,DatabaseUtils.toValues(entity));
        entity.setId(FAKER_ID);
        db.insert(Tables.INGREDIENTS,null,DatabaseUtils.toValues(entity));

        Cursor cursor=db.query(Tables.INGREDIENTS,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(),is(2));

        while(cursor.moveToNext()){
            IngredientEntity result=DatabaseUtils.toIngredient(cursor);
            assertThat(result,notNullValue());
            assertThat(result.getIngredient(),is(entity.getIngredient()));
            assertThat(result.getQuantity(),is(entity.getQuantity()));
            assertThat(result.getMeasure(),is(entity.getMeasure()));
            assertTrue(result.getId()==FAKER_ID||result.getId()==FAKE_ID);
        }
    }

    @Test
    public void queryAllStepsFromDatabase(){
        StepEntity entity= RecipeTestUtils.provideStepEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.STEPS,null,DatabaseUtils.toValues(entity,FAKE_ID));
        entity.setId(FAKER_ID);
        db.insert(Tables.STEPS,null,DatabaseUtils.toValues(entity,FAKER_ID));

        Cursor cursor=db.query(Tables.STEPS,null,null,null,null,null,null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(),is(2));

        while(cursor.moveToNext()){
            StepEntity result=DatabaseUtils.toStep(cursor);
            assertThat(result,notNullValue());
            assertThat(result.getVideoUrl(),is(entity.getVideoUrl()));
            assertThat(result.getShortDescription(),is(entity.getShortDescription()));
            assertThat(result.getDescription(),is(entity.getDescription()));
            assertThat(result.getImageUrl(),is(entity.getImageUrl()));
            assertTrue(result.getId()==FAKER_ID||result.getId()==FAKE_ID);
        }
    }

    @Test
    public void shouldJoinRecipeWithSteps(){
        RecipeEntity recipeEntity=RecipeTestUtils.provideRecipeEntity();
        StepEntity stepEntity= RecipeTestUtils.provideStepEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.RECIPES,null,DatabaseUtils.toValues(recipeEntity));
        db.insert(Tables.STEPS,null, DatabaseUtils.toValues(stepEntity,recipeEntity.getId()));

        String selection=Tables.RECIPES+"."+ RecipeContract.Recipes.RECIPE_ID+"=?";
        String[] selectionArgs={Long.toString(recipeEntity.getId())};
        Cursor cursor=db.query(Tables.RECIPE_JOIN_STEPS,null,selection,selectionArgs,null,null,null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(),is(1));

        while(cursor.moveToNext()){
            StepEntity resultStep=DatabaseUtils.toStep(cursor);
            RecipeEntity resultRecipe=DatabaseUtils.toRecipe(cursor);
            assertThat(resultStep,notNullValue());
            assertThat(resultStep.getVideoUrl(),is(stepEntity.getVideoUrl()));
            assertThat(resultStep.getShortDescription(),is(stepEntity.getShortDescription()));
            assertThat(resultStep.getDescription(),is(stepEntity.getDescription()));
            assertThat(resultStep.getImageUrl(),is(stepEntity.getImageUrl()));
            assertTrue(resultStep.getId()==recipeEntity.getId());
            assertThat(resultRecipe,notNullValue());
            assertTrue(resultRecipe.getId()==recipeEntity.getId());
            assertThat(resultRecipe.getName(),is(recipeEntity.getName()));
            assertThat(resultRecipe.getImageUrl(),is(recipeEntity.getImageUrl()));
            assertThat(resultRecipe.getServings(),is(recipeEntity.getServings()));

        }
    }

    @Test
    public void shouldJoinRecipeWithIngredients(){
        RecipeEntity recipeEntity=RecipeTestUtils.provideRecipeEntity();
        IngredientEntity ingredientEntity= RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity));
        db.insert(Tables.RECIPES,null,DatabaseUtils.toValues(recipeEntity));
        db.insert(Tables.RECIPES_INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity,recipeEntity));

        String selection=Tables.RECIPES+"."+ RecipeContract.Recipes.RECIPE_ID+"=?";
        String[] selectionArgs={Long.toString(recipeEntity.getId())};
        Cursor cursor=db.query(Tables.RECIPE_JOIN_INGREDIENTS,null,selection,selectionArgs,null,null,null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(),is(1));

        while(cursor.moveToNext()) {
            RecipeEntity resultRecipe = DatabaseUtils.toRecipe(cursor);
            assertThat(resultRecipe, notNullValue());
            assertTrue(resultRecipe.getId() == recipeEntity.getId());
            assertThat(resultRecipe.getName(), is(recipeEntity.getName()));
            assertThat(resultRecipe.getImageUrl(), is(recipeEntity.getImageUrl()));
            assertThat(resultRecipe.getServings(), is(recipeEntity.getServings()));
        }

    }

    @Test
    public void shouldJoinIngredientWithRecipes(){
        RecipeEntity recipeEntity=RecipeTestUtils.provideRecipeEntity();
        IngredientEntity ingredientEntity= RecipeTestUtils.provideIngredientEntity();
        SQLiteDatabase db=recipeDatabaseHelper.getWritableDatabase();
        db.insert(Tables.INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity));
        db.insert(Tables.RECIPES,null,DatabaseUtils.toValues(recipeEntity));
        db.insert(Tables.RECIPES_INGREDIENTS,null,DatabaseUtils.toValues(ingredientEntity,recipeEntity));

        String selection=Tables.INGREDIENTS+"."+ RecipeContract.Ingredients.INGREDIENT_ID+"=?";
        String[] selectionArgs={Long.toString(ingredientEntity.getId())};
        Cursor cursor=db.query(Tables.INGREDIENTS_JOIN_RECIPE,null,selection,selectionArgs,null,null,null);
        assertThat(cursor,notNullValue());
        assertThat(cursor.getCount(),is(1));

        while(cursor.moveToNext()) {
            IngredientEntity resultIngredient = DatabaseUtils.toIngredient(cursor);
            assertThat(resultIngredient, notNullValue());
            assertThat(resultIngredient.getIngredient(), is(ingredientEntity.getIngredient()));
            assertThat(resultIngredient.getQuantity(), is(ingredientEntity.getQuantity()));
            assertThat(resultIngredient.getMeasure(), is(ingredientEntity.getMeasure()));
            assertTrue(resultIngredient.getId() == ingredientEntity.getId());
        }
    }
}

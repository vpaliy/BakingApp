package data.local;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.support.compat.BuildConfig;
import com.vpaliy.bakingapp.data.local.DatabaseUtils;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import org.mockito.Mockito;
import data.RecipeTestUtils;
import org.robolectric.RobolectricTestRunner;
import com.vpaliy.bakingapp.data.local.RecipeContract.Recipes;
import com.vpaliy.bakingapp.data.local.RecipeContract.Ingredients;
import com.vpaliy.bakingapp.data.local.RecipeContract.Steps;
import com.vpaliy.bakingapp.data.model.StepEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class DatabaseUtilsTest {

    @Test
    public void shouldConvertRecipeEntityToContentValues(){
        RecipeEntity entity= RecipeTestUtils.provideRecipeEntity();
        ContentValues values= DatabaseUtils.toValues(entity);
        assertThat(values,notNullValue());
        assertThat(values.getAsInteger(Recipes.RECIPE_ID),is(entity.getId()));
        assertThat(values.getAsString(Recipes.RECIPE_IMAGE_URL),is(entity.getImageUrl()));
        assertThat(values.getAsString(Recipes.RECIPE_NAME),is(entity.getName()));
        assertThat(values.getAsInteger(Recipes.RECIPE_SERVINGS),is(entity.getServings()));
    }

    @Test
    public void shouldConvertIngredientEntityToContentValues(){
        IngredientEntity entity= RecipeTestUtils.provideIngredientEntity();
        ContentValues values=DatabaseUtils.toValues(entity);
        assertThat(values,notNullValue());
        assertThat(values.getAsInteger(Ingredients.INGREDIENT_ID),is(entity.getId()));
        assertThat(values.getAsString(Ingredients.INGREDIENT_MEASURE),is(entity.getMeasure()));
        assertThat(values.getAsDouble(Ingredients.INGREDIENT_QUANTITY),is(entity.getQuantity()));
        assertThat(values.getAsString(Ingredients.INGREDIENT_NAME),is(entity.getIngredient()));
    }

    @Test
    public void shouldConvertStepEntityToContentValues(){
        StepEntity entity=RecipeTestUtils.provideStepEntity();
        ContentValues values=DatabaseUtils.toValues(entity,RecipeTestUtils.FAKE_ID);
        assertThat(values,notNullValue());
        assertThat(values.getAsInteger(Steps.STEP_ID),is(entity.getId()));
        assertThat(values.getAsString(Steps.STEP_DESCRIPTION),is(entity.getDescription()));
        assertThat(values.getAsString(Steps.STEP_SHORT_DESCRIPTION),is(entity.getShortDescription()));
        assertThat(values.getAsString(Steps.STEP_IMAGE_URL),is(entity.getImageUrl()));
        assertThat(values.getAsString(Steps.STEP_VIDEO_URL),is(entity.getVideoUrl()));
        assertThat(values.getAsInteger(Steps.STEP_RECIPE_ID),is(RecipeTestUtils.FAKE_ID));
    }

    @Test
    public void shouldConvertCursorToRecipeEntity(){
        RecipeEntity entity=RecipeTestUtils.provideRecipeEntity();
        Cursor cursor=createMockCursorFor(entity);
        RecipeEntity result=DatabaseUtils.toRecipe(cursor);
        assertThat(result,notNullValue());
        assertThat(result.getId(),is(entity.getId()));
        assertThat(result.getServings(),is(entity.getServings()));
        assertThat(result.getName(),is(entity.getName()));
        assertThat(result.getImageUrl(),is(entity.getImageUrl()));

    }

    @Test
    public void shouldConvertCursorToStepEntity(){
        StepEntity entity=RecipeTestUtils.provideStepEntity();
        Cursor cursor=createMockCursorFor(entity,RecipeTestUtils.FAKE_ID);
        StepEntity result=DatabaseUtils.toStep(cursor);
        assertThat(result,notNullValue());
        assertThat(result.getId(),is(entity.getId()));
        assertThat(result.getImageUrl(),is(entity.getImageUrl()));
        assertThat(result.getDescription(),is(entity.getDescription()));
        assertThat(result.getShortDescription(),is(entity.getShortDescription()));
        assertThat(result.getVideoUrl(),is(entity.getVideoUrl()));
    }

    @Test
    public void shouldConvertCursorToIngredientEntity(){
        IngredientEntity entity=RecipeTestUtils.provideIngredientEntity();
        Cursor cursor=createMockCursorFor(entity);
        IngredientEntity result=DatabaseUtils.toIngredient(cursor);
        assertThat(result,notNullValue());
        assertThat(result.getId(),is(entity.getId()));
        assertThat(result.getIngredient(),is(entity.getIngredient()));
        assertThat(result.getMeasure(),is(entity.getMeasure()));
        assertThat(result.getQuantity(),is(entity.getQuantity()));
    }

    private Cursor createMockCursorFor(StepEntity entity, int recipeId){
        Cursor cursor= Mockito.mock(Cursor.class);
        for(int index=0;index<Steps.COLUMNS.length;index++){
            given(cursor.getColumnIndex(Steps.COLUMNS[index])).willReturn(index);
        }

        when(cursor.getInt(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Steps.COLUMNS[columnIndex];
            switch (argument){
                case Steps.STEP_ID:
                    return entity.getId();
                case Steps.STEP_RECIPE_ID:
                    return recipeId;
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });

        when(cursor.getString(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Steps.COLUMNS[columnIndex];
            switch (argument){
                case Steps.STEP_DESCRIPTION:
                    return entity.getDescription();
                case Steps.STEP_SHORT_DESCRIPTION:
                    return entity.getShortDescription();
                case Steps.STEP_IMAGE_URL:
                    return entity.getImageUrl();
                case Steps.STEP_VIDEO_URL:
                    return entity.getVideoUrl();
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });
        return cursor;
    }

    private Cursor createMockCursorFor(RecipeEntity entity){
        Cursor cursor= Mockito.mock(Cursor.class);;
        for(int index = 0; index< Recipes.COLUMNS.length; index++) {
            given(cursor.getColumnIndex(Recipes.COLUMNS[index])).willReturn(index);
        }

        when(cursor.getString(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Recipes.COLUMNS[columnIndex];
            switch (argument){
                case Recipes.RECIPE_NAME:
                    return entity.getName();
                case Recipes.RECIPE_IMAGE_URL:
                    return entity.getImageUrl();
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });

        when(cursor.getInt(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Recipes.COLUMNS[columnIndex];
            switch (argument){
                case Recipes.RECIPE_ID:
                    return entity.getId();
                case Recipes.RECIPE_SERVINGS:
                    return entity.getServings();
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });
        return cursor;
    }

    private Cursor createMockCursorFor(IngredientEntity entity){
        Cursor cursor= Mockito.mock(Cursor.class);
        for(int index=0;index<Ingredients.COLUMNS.length;index++){
            given(cursor.getColumnIndex(Ingredients.COLUMNS[index])).willReturn(index);
        }
        when(cursor.getInt(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Ingredients.COLUMNS[columnIndex];
            switch (argument){
                case Ingredients.INGREDIENT_ID:
                    return entity.getId();
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });

        when(cursor.getFloat(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Ingredients.COLUMNS[columnIndex];
            switch (argument){
                case Ingredients.INGREDIENT_QUANTITY:
                    return entity.getQuantity();
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });

        when(cursor.getString(anyInt())).thenAnswer(invocation -> {
            Integer columnIndex=invocation.getArgumentAt(0,Integer.class);
            String argument =Ingredients.COLUMNS[columnIndex];
            switch (argument){
                case Ingredients.INGREDIENT_MEASURE:
                    return entity.getMeasure();
                case Ingredients.INGREDIENT_NAME:
                    return entity.getIngredient();
                default:
                    throw new UnsupportedOperationException("Unknown argument:"+argument);
            }
        });
        return cursor;
    }
}

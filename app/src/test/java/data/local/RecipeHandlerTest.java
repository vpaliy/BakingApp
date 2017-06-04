package data.local;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.compat.BuildConfig;
import com.vpaliy.bakingapp.data.local.DatabaseUtils;
import com.vpaliy.bakingapp.data.local.RecipeHandler;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import java.util.List;
import data.RecipeTestUtils;
import static data.RecipeTestUtils.FAKE_ID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(DatabaseUtils.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class RecipeHandlerTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Mock
    private ContentResolver mockContentResolver;

    @InjectMocks
    private RecipeHandler recipeHandler;

    @Before
    public void setUp()throws Exception{
        PowerMockito.mockStatic(DatabaseUtils.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handlesInsertionsOfRecipeValues(){
        RecipeEntity recipeEntity= RecipeTestUtils.provideRecipeEntity();
        PowerMockito.when(DatabaseUtils.toValues(recipeEntity)).thenReturn(new ContentValues());
        recipeHandler.insert(recipeEntity);
        verify(mockContentResolver).insert(any(Uri.class),any(ContentValues.class));
        PowerMockito.verifyStatic();
        DatabaseUtils.toValues(any(RecipeEntity.class));
    }

    @Test
    public void handlesInsertionOfIngredientValues(){
        List<IngredientEntity> entityList=RecipeTestUtils.provideIngredientEntityList();
        PowerMockito.when(DatabaseUtils.toValues(any(IngredientEntity.class))).thenReturn(new ContentValues());
        recipeHandler.insertIngredients(FAKE_ID,entityList);
        verify(mockContentResolver,times(entityList.size()*3)).insert(any(Uri.class),any(ContentValues.class));
        PowerMockito.verifyStatic(times(entityList.size()));
        DatabaseUtils.toValues(any(IngredientEntity.class));
    }

    @Test
    public void handlesInsertionOfStepValues(){
        List<StepEntity> entityList=RecipeTestUtils.provideStepEntityList();
        PowerMockito.when(DatabaseUtils.toValues(any(StepEntity.class),anyInt())).thenReturn(new ContentValues());
        recipeHandler.insertSteps(FAKE_ID,entityList);
        verify(mockContentResolver,times(entityList.size())).insert(any(Uri.class),any(ContentValues.class));
        PowerMockito.verifyStatic(times(entityList.size()));
        DatabaseUtils.toValues(any(StepEntity.class),anyInt());
    }

    @Test
    public void handlesQueryForSteps(){
        RecipeEntity entity=RecipeTestUtils.provideRecipeEntity();
        Cursor cursor=Mockito.mock(Cursor.class);
        when(cursor.getCount()).thenReturn(3);
        when(cursor.moveToNext())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(cursor.isClosed()).thenReturn(false);
        when(mockContentResolver.query(any(Uri.class),any(),any(),any(),any())).thenReturn(cursor);
        PowerMockito.when(DatabaseUtils.toStep(cursor)).thenReturn(new StepEntity());

        recipeHandler.queryStepsFor(entity);

        verify(mockContentResolver).query(any(),any(),any(),any(),any());
        verify(cursor).getCount();
        verify(cursor,times(4)).moveToNext();
        verify(cursor).isClosed();
        verify(cursor).close();

        PowerMockito.verifyStatic(times(3));
        DatabaseUtils.toStep(cursor);
    }

    @Test
    public void handlesQueryForIngredients(){
        RecipeEntity entity=RecipeTestUtils.provideRecipeEntity();
        Cursor cursor=Mockito.mock(Cursor.class);
        when(cursor.getCount()).thenReturn(3);
        when(cursor.moveToNext())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(cursor.isClosed()).thenReturn(false);
        when(mockContentResolver.query(any(Uri.class),any(),any(),any(),any())).thenReturn(cursor);
        PowerMockito.when(DatabaseUtils.toIngredient(cursor)).thenReturn(new IngredientEntity());

        recipeHandler.queryIngredientsFor(entity);

        verify(mockContentResolver).query(any(Uri.class),any(),any(),any(),any());
        verify(cursor).getCount();
        verify(cursor,times(4)).moveToNext();
        verify(cursor).isClosed();
        verify(cursor).close();

        PowerMockito.verifyStatic(times(3));
        DatabaseUtils.toIngredient(cursor);
    }

    @Test
    public void handlesQueryRecipeById (){
        Cursor cursor=Mockito.mock(Cursor.class);
        when(cursor.isClosed()).thenReturn(false);
        when(mockContentResolver.query(any(Uri.class),any(),any(),any(),any())).thenReturn(cursor);
        PowerMockito.when(DatabaseUtils.toRecipe(cursor)).thenReturn(new RecipeEntity());

        recipeHandler.queryById(FAKE_ID);

        verify(mockContentResolver).query(any(Uri.class),any(),any(),any(),any());
        verify(cursor).isClosed();
        verify(cursor).close();

        PowerMockito.verifyStatic();
        DatabaseUtils.toRecipe(cursor);
    }
}

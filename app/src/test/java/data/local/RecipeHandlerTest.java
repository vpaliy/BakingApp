package data.local;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.compat.BuildConfig;

import com.vpaliy.bakingapp.data.local.DatabaseUtils;
import com.vpaliy.bakingapp.data.local.RecipeContract;
import com.vpaliy.bakingapp.data.local.RecipeHandler;
import com.vpaliy.bakingapp.data.model.IngredientEntity;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.data.model.StepEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.PowerMockitoCore;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import java.util.List;
import data.RecipeTestUtils;
import static data.RecipeTestUtils.FAKE_ID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(DatabaseUtils.class);
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
        recipeHandler.queryStepsFor(entity);
        Cursor cursor=Mockito.mock(Cursor.class);

        when(mockContentResolver.query(any(),any(),any(),any(),any())).thenReturn(cursor);
        PowerMockito.when(DatabaseUtils.toStep(cursor)).thenReturn(new StepEntity());

        verify(mockContentResolver).query(any(),any(),any(),any(),any());
        verify(cursor).getCount();
    }
}

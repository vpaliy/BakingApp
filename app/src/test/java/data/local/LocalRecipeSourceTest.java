package data.local;


import android.content.ContentResolver;
import android.os.Build;
import android.support.compat.BuildConfig;
import com.vpaliy.bakingapp.data.local.LocalRecipeSource;
import com.vpaliy.bakingapp.data.local.RecipeHandler;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.ArrayList;
import common.RecipeTestUtils;
import rx.android.schedulers.AndroidSchedulers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest = Config.NONE,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class LocalRecipeSourceTest{

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private ContentResolver mockContentResolver;

    @Mock
    private RecipeHandler mockRecipeHandler;

    @InjectMocks
    private LocalRecipeSource localRecipeSource;

    @Test
    public void returnsAllRecipes(){
        Mockito.when(mockRecipeHandler.queryAll()).thenReturn(new ArrayList<>());
        localRecipeSource.getRecipes()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::shouldNotBeNull);
        Mockito.verify(mockRecipeHandler).queryAll();
    }

    @Test
    public void insertsRecipeIfRecipeIsNotNull(){
        RecipeEntity entity= RecipeTestUtils.provideRecipeEntity();
        Mockito.when(mockRecipeHandler.insert(eq(entity))).thenReturn(mockRecipeHandler);
        Mockito.when(mockRecipeHandler.insertIngredients(eq(entity.getId()),any())).thenReturn(mockRecipeHandler);
        Mockito.when(mockRecipeHandler.insertSteps(eq(entity.getId()),any())).thenReturn(mockRecipeHandler);

        localRecipeSource.insert(entity);

        Mockito.verify(mockRecipeHandler).insert(eq(entity));
        Mockito.verify(mockRecipeHandler).insertIngredients(eq(entity.getId()),any());
        Mockito.verify(mockRecipeHandler).insertSteps(eq(entity.getId()),any());
    }

    private void shouldNotBeNull(Object object){
        assertTrue(object!=null);
    }
}


package data.local;

import android.os.Build;
import android.support.compat.BuildConfig;

import com.vpaliy.bakingapp.data.local.RecipeDatabaseHelper;
import com.vpaliy.bakingapp.data.local.RecipeProvider;
import com.vpaliy.bakingapp.data.local.RecipeUriMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest = Config.NONE,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class RecipeProviderTest {

    @Mock
    private RecipeDatabaseHelper mockHelper;

    @Mock
    private RecipeUriMatcher matcher;

    @InjectMocks
    private RecipeProvider recipeProvider;

    @Before
    public void setUp(){

    }


}

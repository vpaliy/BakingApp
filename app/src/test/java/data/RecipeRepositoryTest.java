package data;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.compat.BuildConfig;

import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.RecipeRepository;
import com.vpaliy.bakingapp.data.annotation.Local;
import com.vpaliy.bakingapp.data.annotation.Remote;
import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.model.Recipe;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.ArrayList;
import rx.Observable;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest = Config.NONE,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class RecipeRepositoryTest {


    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    @Remote
    private DataSource<RecipeEntity> mockRemote;

    @Mock
    @Local
    private DataSource<RecipeEntity> mockLocal;

    @Mock
    private Mapper<Recipe,RecipeEntity> mockMapper;

    @Mock
    private ConnectivityManager mockManager;

    @Mock
    private NetworkInfo mockInfo;

    @Mock
    private Context context;

    private RecipeRepository recipeRepository;


    @Before
    public void setUp(){
        when(mockLocal.getRecipes()).thenReturn(Observable.empty());
        when(mockRemote.getRecipes()).thenReturn(Observable.empty());
        when(mockMapper.map(any(RecipeEntity.class))).thenReturn(new Recipe());
        when(mockManager.getActiveNetworkInfo()).thenReturn(mockInfo);
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockManager);
        when(mockMapper.map(anyListOf(RecipeEntity.class))).thenReturn(new ArrayList<>());

        recipeRepository=new RecipeRepository(mockLocal,mockRemote,mockMapper,context);
    }


    @Test
    public void returnsRecipesWhenThereIsNetwork(){
        when(mockInfo.isConnectedOrConnecting()).thenReturn(true);
        recipeRepository.getRecipes()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldNotBeNullAndInvokeMap);
        verify(mockRemote).getRecipes();
    }

    @Test
    public void returnsRecipesWhenThereIsNotNetwork(){
        when(mockInfo.isConnectedOrConnecting()).thenReturn(false);
        recipeRepository.getRecipes()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldNotBeNullAndInvokeMap);
        verify(mockLocal).getRecipes();
    }

    private void shouldNotBeNullAndInvokeMap(Object object){
        assertTrue(object!=null);
        verify(mockMapper).map(anyListOf(RecipeEntity.class));
    }

}

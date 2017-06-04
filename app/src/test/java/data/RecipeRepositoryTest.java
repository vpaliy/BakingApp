package data;


import android.content.Context;

import com.vpaliy.bakingapp.data.DataSource;
import com.vpaliy.bakingapp.data.RecipeRepository;
import com.vpaliy.bakingapp.data.mapper.Mapper;
import com.vpaliy.bakingapp.data.model.RecipeEntity;
import com.vpaliy.bakingapp.domain.model.Recipe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class RecipeRepositoryTest {

    @Mock
    private DataSource<RecipeEntity> mockRemote;

    @Mock
    private DataSource<RecipeEntity> mockLocal;

    @Mock
    private Mapper<Recipe,RecipeEntity> mockMapper;

    private RecipeRepository recipeRepository;


    @Before
    public void setUp(){
        when(mockLocal.getRecipes()).thenReturn(Observable.empty());
        when(mockRemote.getRecipes()).thenReturn(Observable.empty());
        Context context= RuntimeEnvironment.application;
        recipeRepository=new RecipeRepository(mockLocal,mockRemote,mockMapper,context);
    }

}

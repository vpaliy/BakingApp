package data.remote;


import com.vpaliy.bakingapp.data.remote.RecipeAPI;
import com.vpaliy.bakingapp.data.remote.RemoteRecipeSource;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@RunWith(MockitoJUnitRunner.class)
public class RemoteRecipeSourceTest {

    @Mock
    private RecipeAPI recipeAPI;

    @InjectMocks
    private RemoteRecipeSource remoteRecipeSource;

    @Test
    public void shouldRequestRecipesFromProvidedApi(){
        Mockito.when(recipeAPI.queryRecipes()).thenReturn(Observable.empty());
        remoteRecipeSource.getRecipes();
        Mockito.verify(recipeAPI).queryRecipes();
    }
}

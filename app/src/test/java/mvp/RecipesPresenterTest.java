package mvp;

import android.accounts.NetworkErrorException;

import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.Presenter;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.View;
import com.vpaliy.bakingapp.mvp.presenter.RecipesPresenter;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import com.vpaliy.bakingapp.utils.scheduler.ImmediateSchedulerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.List;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static common.RecipeTestUtils.provideRecipeList;
import rx.Observable;


@RunWith(MockitoJUnitRunner.class)
public class RecipesPresenterTest {

    @Mock
    private View view;

    @Mock
    private IRepository<Recipe> repository;

    @Mock
    private MessageProvider messageProvider;

    private Presenter presenter;

    @Before
    public void setUp(){
        BaseSchedulerProvider schedulerProvider=new ImmediateSchedulerProvider();
        presenter=new RecipesPresenter(repository,schedulerProvider,messageProvider);
        presenter.attachView(view);
    }

    @Test
    public void showsRecipesOnQueryRecipesMethod(){
        List<Recipe> recipes=provideRecipeList();
        when(repository.getRecipes()).thenReturn(Observable.just(recipes));

        presenter.queryRecipes();

        verify(repository).getRecipes();
        verify(view).setLoading(eq(true));
        verify(view).setLoading(eq(false));
        verify(view).showRecipes(recipes);
    }

    @Test
    public void showsEmptyMessageWhenThereIsNoData(){
        when(repository.getRecipes()).thenReturn(Observable.just(null));

        presenter.queryRecipes();

        verify(repository).getRecipes();
        verify(messageProvider).emptyMessage();
        verify(view).setLoading(eq(true));
        verify(view).setLoading(eq(false));
        verify(view).showMessage(anyString());
    }

    @Test
    public void showsErrorMessageWhenErrorHasOccurred(){
        when(repository.getRecipes()).thenReturn(Observable.error(new NetworkErrorException()));

        presenter.queryRecipes();

        verify(repository).getRecipes();
        verify(messageProvider).noNetworkConnection();
        verify(view).setLoading(eq(true));
        verify(view).setLoading(eq(false));
        verify(view).showMessage(anyString());
    }


}

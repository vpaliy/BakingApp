package mvp;

import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract.Presenter;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract.View;
import com.vpaliy.bakingapp.mvp.presenter.RecipesPresenter;
import com.vpaliy.bakingapp.mvp.presenter.SummaryPresenter;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;
import com.vpaliy.bakingapp.utils.scheduler.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;

import static common.RecipeTestUtils.FAKE_ID;
import static common.RecipeTestUtils.provideRecipeList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SummaryPresenterTest {

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
        presenter=new SummaryPresenter(repository,schedulerProvider,messageProvider);
        presenter.attachView(view);
    }

    @Test
    public void showsRecipeById(){
        Recipe recipe=new Recipe();
        when(repository.getRecipeById(FAKE_ID)).thenReturn(Observable.just(recipe));

        presenter.fetchById(FAKE_ID);

        verify(repository).getRecipeById(eq(FAKE_ID));
        verify(view).showRecipe(recipe);
    }

    @Test
    public void showsEmptyMessageWhenThereIsNoData(){
        when(repository.getRecipeById(FAKE_ID)).thenReturn(Observable.just(null));

        presenter.fetchById(FAKE_ID);

        verify(repository).getRecipeById(eq(FAKE_ID));
        verify(messageProvider).emptyMessage();
        verify(view).showMessage(anyString());
    }
}

package mvp;

import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.Presenter;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.View;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class RecipesPresenterTest {

    @Mock
    private View view;

    @Mock
    private IRepository<Recipe> repository;

    @Mock
    private BaseSchedulerProvider schedulerProvider;

    @Mock
    private MessageProvider messageProvider;

    @InjectMocks
    private Presenter presenter;


    @Before
    public void setUp(){
        presenter.attachView(view);
    }




}

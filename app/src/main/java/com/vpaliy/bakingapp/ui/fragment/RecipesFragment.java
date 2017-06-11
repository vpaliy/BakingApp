package com.vpaliy.bakingapp.ui.fragment;

import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import java.util.List;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract;
import com.vpaliy.bakingapp.mvp.contract.RecipesContract.Presenter;
import com.vpaliy.bakingapp.ui.adapter.RecipesAdapter;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.view.MarginDecoration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;

public class RecipesFragment extends BaseFragment
        implements RecipesContract.View{

    private Presenter presenter;
    private RecipesAdapter adapter;

    @BindView(R.id.recipe_list)
    protected RecyclerView recipeList;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout refresher;

    @Inject
    protected RxBus rxBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_recipes,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            refresher.setOnRefreshListener(()->presenter.queryRecipes());
            adapter=new RecipesAdapter(getContext(),rxBus);
            recipeList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recipeList.addItemDecoration(new MarginDecoration(getContext()));
            recipeList.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null) adapter.resume();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void setLoading(boolean isLoading) {
        refresher.setRefreshing(isLoading);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showRecipes(@NonNull List<Recipe> recipes) {
        adapter.setData(recipes);
    }

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(BakingApp.appInstance().appComponent())
                .build().inject(this);
    }
}

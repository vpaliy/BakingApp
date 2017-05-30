package com.vpaliy.bakingapp.ui.fragment;


import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.di.component.DaggerViewComponent;
import com.vpaliy.bakingapp.di.module.PresenterModule;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeSummaryContract.Presenter;
import com.vpaliy.bakingapp.ui.adapter.StepsAdapter;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.view.MarginDecoration;
import com.vpaliy.bakingapp.utils.Constants;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;

public class RecipeSummaryFragment extends BaseFragment
        implements RecipeSummaryContract.View{

    private Presenter presenter;
    private StepsAdapter adapter;
    private int recipeId;

    @BindView(R.id.recipe_ingredients)
    protected ViewGroup ingredients;

    @BindView(R.id.recipe_steps)
    protected RecyclerView recipeSteps;

    @Inject
    protected RxBus rxBus;

    public static RecipeSummaryFragment newInstance(Bundle bundle){
        RecipeSummaryFragment fragment=new RecipeSummaryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        this.recipeId=savedInstanceState.getInt(Constants.EXTRA_RECIPE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_recipe_summary,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null) adapter.resume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.fetchById(recipeId);
            adapter=new StepsAdapter(getContext(),rxBus);
            recipeSteps.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recipeSteps.addItemDecoration(new MarginDecoration(getContext()));
            recipeSteps.setAdapter(adapter);
        }
    }


    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(BakingApp.appInstance().appComponent())
                .build().inject(this);
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showRecipe(@NonNull Recipe recipe) {
        showIngredients(recipe.getIngredients());
        showSteps(recipe.getSteps());
    }

    private void showIngredients(@NonNull List<Ingredient> ingredientList){

    }

    private void showSteps(@NonNull List<Step> steps){
        adapter.setData(steps);
    }
}

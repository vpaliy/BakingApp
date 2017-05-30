package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.Presenter;
import com.vpaliy.bakingapp.mvp.presenter.RecipeStepsPresenter;
import com.vpaliy.bakingapp.mvp.presenter.RecipeStepsPresenter.StepsWrapper;
import com.vpaliy.bakingapp.ui.bus.event.OnStepClickEvent;
import com.vpaliy.bakingapp.ui.fragment.RecipeStepsFragment;
import com.vpaliy.bakingapp.ui.fragment.RecipeSummaryFragment;
import com.vpaliy.bakingapp.utils.Constants;
import com.vpaliy.bakingapp.utils.messenger.Messenger;
import android.support.v4.app.FragmentManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class RecipeDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            setUI(getIntent().getExtras());
        }
    }

    private void setUI(Bundle args){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, RecipeSummaryFragment.newInstance(args), Constants.SUMMARY_TAG)
                .commit();
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof OnStepClickEvent){
            showSteps(OnStepClickEvent.class.cast(event));
        }
    }

    private void showSteps(OnStepClickEvent clickEvent){
        FragmentManager manager=getSupportFragmentManager();
        if(manager.findFragmentByTag(Constants.STEPS_TAG)!=null){
            manager.beginTransaction()
                    .hide(manager.findFragmentByTag(Constants.SUMMARY_TAG))
                    .show(manager.findFragmentByTag(Constants.STEPS_TAG))
                    .commit();
        }else{
            StepsWrapper wrapper=StepsWrapper.wrap(clickEvent.steps,clickEvent.currentStep);
            Presenter presenter=new RecipeStepsPresenter(wrapper,new Messenger(this));
            RecipeStepsFragment fragment=new RecipeStepsFragment();
            fragment.attachPresenter(presenter);
            manager.beginTransaction()
                    .hide(manager.findFragmentByTag(Constants.SUMMARY_TAG))
                    .show(fragment)
                    .commit();
        }
    }


    @Override
    void initializeDependencies() {
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }
}

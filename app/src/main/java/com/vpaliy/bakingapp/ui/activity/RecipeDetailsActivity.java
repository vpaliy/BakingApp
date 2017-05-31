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

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;


public class RecipeDetailsActivity extends BaseActivity {

    private static final String TAG=RecipeDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Log.d(TAG,"onActivityCreated()");
        if(savedInstanceState==null){
            setUI(getIntent().getExtras());
        }
    }

    private void setUI(Bundle args){
        Log.d(TAG,"create fragment");
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
        Log.d(TAG,"onStepClicked()");
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
                    .addToBackStack(Constants.SUMMARY_TAG)
                    .hide(manager.findFragmentByTag(Constants.SUMMARY_TAG))
                    .add(R.id.frame,fragment,Constants.STEPS_TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager=getSupportFragmentManager();
        if(manager.getBackStackEntryCount()>0){
            manager.popBackStack();
            manager.beginTransaction()
                    .show(manager.findFragmentByTag(Constants.SUMMARY_TAG))
                    .commit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    void initializeDependencies() {
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }
}

package com.vpaliy.bakingapp.ui.activity;

import android.os.Bundle;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.Presenter;
import com.vpaliy.bakingapp.mvp.presenter.StepsPresenter;
import com.vpaliy.bakingapp.mvp.presenter.StepsPresenter.StepsWrapper;
import com.vpaliy.bakingapp.ui.bus.event.OnChangeToolbarEvent;
import com.vpaliy.bakingapp.ui.bus.event.OnChangeVisibilityEvent;
import com.vpaliy.bakingapp.ui.bus.event.OnStepClickEvent;
import com.vpaliy.bakingapp.ui.fragment.StepsFragment;
import com.vpaliy.bakingapp.ui.fragment.SummaryFragment;
import com.vpaliy.bakingapp.utils.Constants;
import com.vpaliy.bakingapp.utils.Permissions;
import com.vpaliy.bakingapp.utils.messenger.Messenger;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends BaseActivity {

    private static final String TAG="+"+DetailsActivity.class.getSimpleName();

    @BindView(R.id.action_bar)
    protected Toolbar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        Log.d(TAG,"onCreate()");
        setActionBar();
        if(savedInstanceState==null){
            setUI(getIntent().getExtras());
        }
    }

    private void setUI(Bundle args){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, SummaryFragment.newInstance(args), Constants.SUMMARY_TAG)
                .commit();
    }

    private void setActionBar(){
        setSupportActionBar(actionBar);
        if(getSupportActionBar()!=null){
            if(actionBar.getTitle()!=null)
            Log.d(TAG,actionBar.getTitle().toString());
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof OnStepClickEvent){
            showSteps(OnStepClickEvent.class.cast(event));
        }else if(event instanceof OnChangeToolbarEvent){
            changeToolbar(OnChangeToolbarEvent.class.cast(event));
        }else if(event instanceof OnChangeVisibilityEvent){
            changeVisibility(OnChangeVisibilityEvent.class.cast(event));
        }
    }

    private void changeToolbar(OnChangeToolbarEvent event){
        actionBar.setTitle(event.text);
    }

    private void changeVisibility(OnChangeVisibilityEvent event){
        if(!event.visible){
            if(Permissions.checkForVersion(19)){
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
            if(getSupportActionBar()!=null){
                getSupportActionBar().hide();
            }
        }else{
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
            if(getSupportActionBar()!=null){
                getSupportActionBar().show();
            }
        }
    }

    private void showSteps(OnStepClickEvent clickEvent){
        FragmentManager manager=getSupportFragmentManager();
        if(manager.findFragmentByTag(Constants.STEPS_TAG)!=null){
            manager.beginTransaction()
                    .remove(manager.findFragmentByTag(Constants.SUMMARY_TAG))
                    .show(manager.findFragmentByTag(Constants.STEPS_TAG))
                    .commit();
        }else{
            StepsWrapper wrapper=StepsWrapper.wrap(clickEvent.steps,clickEvent.currentStep);
            Presenter presenter=new StepsPresenter(wrapper,new Messenger(this));
            StepsFragment fragment=new StepsFragment();
            fragment.attachPresenter(presenter);
            manager.beginTransaction()
                    .addToBackStack(Constants.SUMMARY_TAG)
                    .remove(manager.findFragmentByTag(Constants.SUMMARY_TAG))
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
    void initializeDependencies() {
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }


}

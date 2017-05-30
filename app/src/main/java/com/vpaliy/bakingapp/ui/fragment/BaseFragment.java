package com.vpaliy.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();
    }

    void bind(View root){
        if(root!=null){
            unbinder= ButterKnife.bind(this,root);
        }
    }

    abstract void initializeDependencies();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) unbinder.unbind();
    }
}

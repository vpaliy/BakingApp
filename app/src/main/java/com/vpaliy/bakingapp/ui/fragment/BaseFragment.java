package com.vpaliy.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

public abstract class BaseFragment extends Fragment {

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();
    }

    abstract void initializeDependencies();
}

package com.vpaliy.bakingapp.mvp;


import android.support.annotation.NonNull;

public interface MessageProvider {
    @NonNull String noNetworkConnection();
    @NonNull String emptyMessage();
    @NonNull String errorHasOccurred();
}

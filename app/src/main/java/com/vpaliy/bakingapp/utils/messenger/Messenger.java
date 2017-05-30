package com.vpaliy.bakingapp.utils.messenger;


import android.content.Context;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.mvp.MessageProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class Messenger implements MessageProvider{

    private final Context context;

    @Inject
    public Messenger(@NonNull Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public String noNetworkConnection() {
        return context.getString(R.string.message_no_network);
    }

    @NonNull
    @Override
    public String errorHasOccurred() {
        return context.getString(R.string.message_error_has_occurred);
    }

    @NonNull
    @Override
    public String emptyMessage() {
        return context.getString(R.string.message_empty_query);
    }
}

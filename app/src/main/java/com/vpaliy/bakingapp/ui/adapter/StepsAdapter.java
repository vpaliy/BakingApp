package com.vpaliy.bakingapp.ui.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.bakingapp.domain.model.Step;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import android.support.annotation.NonNull;

public class StepsAdapter extends AbstractAdapter<Step>{

    public StepsAdapter(@NonNull Context context,
                        @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public  class StepViewHolder extends
            AbstractAdapter<Step>.AbstractViewHolder{

        public StepViewHolder(View itemView){
            super(itemView);
        }

        void onBind(){}
    }


    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

}

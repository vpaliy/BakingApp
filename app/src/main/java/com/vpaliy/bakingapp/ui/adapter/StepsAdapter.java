package com.vpaliy.bakingapp.ui.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.model.Step;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.bus.event.OnStepClickEvent;

import android.support.annotation.NonNull;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends AbstractAdapter<Step>{

    private int highlightedPosition=-1;

    public StepsAdapter(@NonNull Context context,
                        @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class StepViewHolder extends AbstractAdapter<Step>.AbstractViewHolder
            implements View.OnClickListener{

        @BindView(R.id.step_short_description)
        TextView shortDescription;

        public StepViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!isLocked()){
                lock();
                rxBus.send(OnStepClickEvent.click(data, getAdapterPosition()));
            }
        }

        void onBind(){
            Step step=at(getAdapterPosition());
            shortDescription.setText(step.getShortDescription());
            if(highlightedPosition==getAdapterPosition()){
                itemView.setBackgroundResource(R.drawable.background_selected);
            }else{
                itemView.setBackgroundResource(R.drawable.background_simple);
            }
        }
    }

    public void highlightPosition(int position){
        if(highlightedPosition!=position){
            int prev=highlightedPosition;
            this.highlightedPosition=position;
            notifyItemChanged(highlightedPosition);
            if(prev!=-1){
                notifyItemChanged(prev);
            }
        }
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_details_steps_item,parent,false);
        return new StepViewHolder(root);
    }

}

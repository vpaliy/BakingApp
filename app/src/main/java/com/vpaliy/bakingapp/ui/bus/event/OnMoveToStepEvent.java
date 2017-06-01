package com.vpaliy.bakingapp.ui.bus.event;


public class OnMoveToStepEvent {

    public final int step;

    private OnMoveToStepEvent(int step){
        this.step=step;
    }

    public static OnMoveToStepEvent move(int step){
        return new OnMoveToStepEvent(step);
    }
}

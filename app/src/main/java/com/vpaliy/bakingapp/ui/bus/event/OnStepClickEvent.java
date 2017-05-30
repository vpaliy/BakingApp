package com.vpaliy.bakingapp.ui.bus.event;


import com.vpaliy.bakingapp.domain.model.Step;

import java.util.List;

public class OnStepClickEvent {

    public final List<Step> steps;
    public final int currentStep;

    private OnStepClickEvent(List<Step> steps,
                             int currentStep){
        this.steps=steps;
        this.currentStep=currentStep;
    }

    public static OnStepClickEvent click(List<Step> steps,
                                         int currentStep){
        return new OnStepClickEvent(steps,currentStep);
    }
}

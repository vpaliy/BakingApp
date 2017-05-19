package com.vpaliy.bakingapp.data.mapper;

import com.vpaliy.bakingapp.data.model.StepEntity;
import com.vpaliy.bakingapp.domain.model.Step;

public class StepMapper implements Mapper<Step,StepEntity> {

    @Override
    public Step map(StepEntity stepEntity) {
        Step step=new Step();
        step.setImageUrl(stepEntity.getImageUrl());
        step.setDescription(stepEntity.getDescription());
        step.setShortDescription(stepEntity.getShortDescription());
        step.setVideoUrl(stepEntity.getVideoUrl());
        step.setStepId(stepEntity.getId());
        return step;
    }

    @Override
    public StepEntity reverseMap(Step step) {
        StepEntity stepEntity=new StepEntity();
        stepEntity.setId(step.getStepId());
        stepEntity.setVideoUrl(step.getVideoUrl());
        stepEntity.setShortDescription(step.getShortDescription());
        stepEntity.setImageUrl(step.getImageUrl());
        stepEntity.setDescription(step.getDescription());
        return stepEntity;
    }
}

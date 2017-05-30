package com.vpaliy.bakingapp.mvp.presenter;


import android.support.annotation.NonNull;

import com.vpaliy.bakingapp.domain.model.Step;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.View;
import java.util.List;

public class RecipeStepsPresenter implements RecipeStepsContract.Presenter{

    private View view;
    private List<Step> steps;
    private MessageProvider messageProvider;
    private Step currentStep;
    private int currentIndex;

    public RecipeStepsPresenter(@NonNull List<Step> steps,
                                @NonNull MessageProvider messageProvider){
        this.steps=steps;
        this.messageProvider=messageProvider;
        this.currentIndex=0;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void stop() {
        view.pauseVideo();
    }

    @Override
    public void showCurrent() {
        Step step=currentStep;
        if(currentIndex<=steps.size()){
            step=steps.get(currentIndex);
        }
        this.currentStep=step;
        manageButtons();
        if(currentStep==null){
            view.showMessage(messageProvider.emptyMessage());
            return;
        }
        view.showDescription(currentStep.getDescription());
        if(currentStep.getVideoUrl()!=null){
            view.playVideo(currentStep.getVideoUrl());
        }else if(currentStep.getImageUrl()!=null){
            view.showImage(currentStep.getImageUrl());
        }

    }

    private void manageButtons(){
        if(currentIndex==0){
            view.hidePrevButton();
        }else{
            view.showPrevButton();
        }

        if(currentIndex>=steps.size()){
            view.hideNextButton();
        }else{
            view.showNextButton();
        }
    }

    @Override
    public void showNext() {
        currentIndex++;
        showCurrent();
    }

    @Override
    public void showPrev() {
        currentIndex--;
        showCurrent();
    }
}

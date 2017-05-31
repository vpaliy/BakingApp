package com.vpaliy.bakingapp.mvp.presenter;

import com.vpaliy.bakingapp.domain.model.Step;
import com.vpaliy.bakingapp.mvp.MessageProvider;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract;
import com.vpaliy.bakingapp.mvp.contract.RecipeStepsContract.View;
import java.util.List;

import android.support.annotation.NonNull;

public class RecipeStepsPresenter implements RecipeStepsContract.Presenter{

    private View view;
    private MessageProvider messageProvider;
    private Step currentStep;
    private StepsWrapper wrapper;

    public RecipeStepsPresenter(@NonNull StepsWrapper wrapper,
                                @NonNull MessageProvider messageProvider){
        this.wrapper=wrapper;
        this.messageProvider=messageProvider;
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
        if(wrapper.currentIndex <=wrapper.count()){
            step=wrapper.current();
        }
        currentStep=step;
        if(currentStep==null){
            view.showMessage(messageProvider.emptyMessage());
            return;
        }
        view.showPageNumber(wrapper.currentIndex+1,wrapper.count());
        view.showDescription(currentStep.getShortDescription(),currentStep.getDescription());
        if(currentStep.getVideoUrl()!=null && !currentStep.getVideoUrl().isEmpty()){
            view.playVideo(currentStep.getVideoUrl());
        }else if(currentStep.getImageUrl()!=null){
            view.hidePlayer();
            view.showImage(currentStep.getImageUrl());
        }
        manageButtons();
    }

    private void manageButtons(){
        if(wrapper.currentIndex==0){
            view.hidePrevButton();
        }else{
            view.showPrevButton();
        }

        if(wrapper.currentIndex>=wrapper.count()-1){
            view.hideNextButton();
        }else{
            view.showNextButton();
        }
    }

    @Override
    public void showNext() {
        wrapper.currentIndex++;
        showCurrent();
    }

    @Override
    public void showPrev() {
        wrapper.currentIndex--;
        showCurrent();
    }

    public static class StepsWrapper {
        private int currentIndex;
        private List<Step> steps;

        int count(){
            return steps.size();
        }

        Step current(){
            return steps.get(currentIndex);
        }

        private StepsWrapper(List<Step> steps,int currentStep){
            this.steps=steps;
            this.currentIndex =currentStep;
        }

        public static StepsWrapper wrap(List<Step> steps, int currentStep){
            return new StepsWrapper(steps,currentStep);
        }
    }
}

package com.vpaliy.bakingapp.domain.model;


public class Step {

    private int stepId;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStepId() {
        return stepId;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}

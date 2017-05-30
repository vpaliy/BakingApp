package com.vpaliy.bakingapp.data.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecipeEntity {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("ingredients")
    private List<IngredientEntity> ingredients;

    @SerializedName("steps")
    private List<StepEntity> steps;

    @SerializedName("servings")
    private int servings;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public List<StepEntity> getSteps() {
        return steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<StepEntity> steps) {
        this.steps = steps;
    }
}

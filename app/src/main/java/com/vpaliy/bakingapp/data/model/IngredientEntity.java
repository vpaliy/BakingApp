package com.vpaliy.bakingapp.data.model;


import com.google.gson.annotations.SerializedName;

public class IngredientEntity {

    private int id;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    @SerializedName("quantity")
    private double quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }
}

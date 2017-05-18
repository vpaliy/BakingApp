package com.vpaliy.backingapp.domain.model;


public class Ingredient {

    private String id;
    private String measure;
    private String ingredient;
    private int quantity;

    public void setId(String id) {
        this.id = id;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }
}

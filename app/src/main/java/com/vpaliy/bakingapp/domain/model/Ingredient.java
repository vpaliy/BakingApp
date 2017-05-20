package com.vpaliy.bakingapp.domain.model;


public class Ingredient {

    private int id;
    private String measure;
    private String ingredient;
    private double quantity;

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

    public int getId() {
        return id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }
}

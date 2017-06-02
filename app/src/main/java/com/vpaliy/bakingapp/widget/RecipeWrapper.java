package com.vpaliy.bakingapp.widget;


import android.os.Parcel;
import android.os.Parcelable;

import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.domain.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeWrapper implements Parcelable{

    private List<Recipe> recipes;

    public RecipeWrapper(List<Recipe> recipes){
        this.recipes=recipes;
    }

    public RecipeWrapper(Parcel in){
        readRecipes(in);
        if(recipes!=null) {
            for (Recipe recipe : recipes) {
                List<Ingredient> ingredients=readIngredients(in);
                if(ingredients!=null) recipe.setIngredients(ingredients);
                List<Step> steps=readSteps(in);
                if(steps!=null) recipe.setSteps(steps);
            }
        }
    }

    private List<Step> readSteps(Parcel parcel){
        int size=parcel.readInt();
        if(size!=-1){
            List<Step> steps=new ArrayList<>(size);
            for(int index=0;index<size;index++){
                Step step=new Step();
                step.setStepId(parcel.readInt());
                step.setImageUrl(parcel.readString());
                step.setShortDescription(parcel.readString());
                step.setDescription(parcel.readString());
                step.setVideoUrl(parcel.readString());
                steps.add(step);
            }
            return steps;
        }
        return null;
    }


    private void writeSteps(List<Step> steps, Parcel parcel){
        if(steps==null){
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(steps.size());
        for(Step step:steps){
            parcel.writeInt(step.getStepId());
            parcel.writeString(step.getImageUrl());
            parcel.writeString(step.getShortDescription());
            parcel.writeString(step.getDescription());
            parcel.writeString(step.getVideoUrl());
        }
    }

    private void writeIngredients(List<Ingredient> ingredients, Parcel parcel){
        if(ingredients==null){
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(ingredients.size());
        for(Ingredient ingredient:ingredients){
            parcel.writeString(ingredient.getIngredient());
            parcel.writeString(ingredient.getMeasure());
            parcel.writeInt(ingredient.getId());
            parcel.writeDouble(ingredient.getQuantity());
        }
    }

    private List<Ingredient> readIngredients(Parcel parcel){
        int size=parcel.readInt();
        if(size!=-1){
            List<Ingredient> result=new ArrayList<>(size);
            for(int index=0;index<size;index++){
                Ingredient ingredient=new Ingredient();
                ingredient.setIngredient(parcel.readString());
                ingredient.setMeasure(parcel.readString());
                ingredient.setId(parcel.readInt());
                ingredient.setQuantity(parcel.readDouble());
                result.add(ingredient);
            }
            return result;
        }
        return null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(recipes==null){
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(recipes.size());
        for(Recipe recipe:recipes){
            writeRecipeData(recipe,dest);
            writeIngredients(recipe.getIngredients(),dest);
            writeSteps(recipe.getSteps(),dest);
        }
    }


    private void writeRecipeData(Recipe recipe, Parcel parcel){
        parcel.writeString(recipe.getImageUrl());
        parcel.writeString(recipe.getName());
        parcel.writeInt(recipe.getId());
        parcel.writeInt(recipe.getServings());
    }

    private void readRecipes(Parcel parcel){
        int size=parcel.readInt();
        if(size!=-1){
            this.recipes=new ArrayList<>(size);
            for(int index=0;index<recipes.size();index++){
                Recipe recipe=new Recipe();
                recipe.setImageUrl(parcel.readString());
                recipe.setName(parcel.readString());
                recipe.setId(parcel.readInt());
                recipe.setServings(recipe.getServings());
            }
        }

    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<RecipeWrapper> CREATOR=new Creator<RecipeWrapper>() {
        @Override
        public RecipeWrapper createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public RecipeWrapper[] newArray(int size) {
            return new RecipeWrapper[0];
        }
    };

}

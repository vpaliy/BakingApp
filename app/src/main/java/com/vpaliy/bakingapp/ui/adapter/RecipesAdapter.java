package com.vpaliy.bakingapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.model.Recipe;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import butterknife.BindView;

public class RecipesAdapter extends
        RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private LayoutInflater inflater;
    private List<Recipe> data;
    private SparseIntArray colorMap;
    private final RandomColor randomColor;

    public RecipesAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
        this.colorMap=new SparseIntArray();
        this.randomColor=new RandomColor();
    }

    public void setData(List<Recipe> data) {
        if(data!=null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_card)
        CardView recipeCard;

        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void applyColor(){
            int color=colorMap.get(getAdapterPosition());
            if(color==-1){
                color=randomColor.randomColor();
                colorMap.put(getAdapterPosition(),color);
            }
            recipeCard.setBackgroundColor(color);
        }
        void bindData(){
            applyColor();
            Recipe recipe=at(getAdapterPosition());
            recipeTitle.setText(recipe.getName());
        }
    }

    private Recipe at(int index){
        return data.get(index);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(inflater.inflate(R.layout.adapter_recipe_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

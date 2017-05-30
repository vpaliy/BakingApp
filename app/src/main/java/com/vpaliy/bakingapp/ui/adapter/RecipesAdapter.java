package com.vpaliy.bakingapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.bus.event.OnRecipeClickEvent;
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
    private RxBus rxBus;
    private final RandomColor randomColor;
    private boolean clicked;

    public RecipesAdapter(@NonNull Context context,
                          @NonNull RxBus rxBus){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
        this.colorMap=new SparseIntArray();
        this.randomColor=new RandomColor();
        this.rxBus=rxBus;
    }

    public void setData(List<Recipe> data) {
        if(data!=null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void resume(){
        clicked=true;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.recipe_card)
        CardView recipeCard;

        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(!clicked){
                clicked=true;
                OnRecipeClickEvent click=OnRecipeClickEvent.click(at(getAdapterPosition()).getId());
                rxBus.send(click);
            }
        }

        void applyColor(){
            int color=colorMap.get(getAdapterPosition(),-1);
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
            Glide.with(inflater.getContext())
                    .load("http://assets.epicurious.com/photos/54ac95e819925f464b3ac37e/6:4/w_620%2Ch_413/51229210_nutella-pie_1x1.jpg")
                    .centerCrop()
                    .into(recipeImage);

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

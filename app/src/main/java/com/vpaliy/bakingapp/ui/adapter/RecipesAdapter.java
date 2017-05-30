package com.vpaliy.bakingapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.bakingapp.domain.model.Recipe;
import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;

public class RecipesAdapter extends
        RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private LayoutInflater inflater;
    private List<Recipe> data;

    public RecipesAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
    }

    public void setData(List<Recipe> data) {
        if(data!=null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public RecipeViewHolder(View itemView){
            super(itemView);
        }

        void bindData(){}
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
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

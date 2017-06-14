package com.vpaliy.bakingapp.ui.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.ui.bus.RxBus;
import com.vpaliy.bakingapp.ui.bus.event.OnRecipeClickEvent;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;

import android.support.annotation.NonNull;
import butterknife.BindView;

import static com.vpaliy.bakingapp.utils.StringUtils.mergeColoredText;

public class RecipesAdapter extends AbstractAdapter<Recipe>{

    public RecipesAdapter(@NonNull Context context,
                          @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class RecipeViewHolder extends AbstractAdapter<Recipe>.AbstractViewHolder
            implements View.OnClickListener{

        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        @BindView(R.id.step_label)
        TextView steps;

        @BindView(R.id.servings_label)
        TextView servings;

        RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(!isLocked()){
                lock();
                Recipe recipe=at(getAdapterPosition());
                OnRecipeClickEvent click=OnRecipeClickEvent.click(recipe.getId());
                rxBus.send(click);
            }
        }

        void onBind(){
            Recipe recipe=at(getAdapterPosition());
            recipeTitle.setText(recipe.getName());
            if(!TextUtils.isEmpty(recipe.getImageUrl())) {
                Glide.with(inflater.getContext())
                        .load(recipe.getImageUrl())
                        .centerCrop()
                        .into(recipeImage);
            }
            Context context=inflater.getContext();
            String numberText=Integer.toString(recipe.getSteps().size());
            steps.setText(mergeColoredText(context.getString(R.string.steps_label),numberText,
                        ContextCompat.getColor(context,R.color.colorPrimary),
                        ContextCompat.getColor(context,R.color.red_color)));
            numberText=Integer.toString(recipe.getServings());
            servings.setText(mergeColoredText(context.getString(R.string.servings_label),numberText,
                    ContextCompat.getColor(context,R.color.colorPrimary),
                    ContextCompat.getColor(context,R.color.red_color)));
        }

    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(inflater.inflate(R.layout.adapter_recipe_item,parent,false));
    }

}

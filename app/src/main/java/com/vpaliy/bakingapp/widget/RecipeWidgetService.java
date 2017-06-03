package com.vpaliy.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.model.Ingredient;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.Constants;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViewsService;

import static com.vpaliy.bakingapp.utils.WidgetUtils.convertFromJsonString;

public class RecipeWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String jsonString=intent.getStringExtra(Constants.EXTRA_WIDGET_DATA);
        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        return new RecipeRemoteFactory(getApplicationContext(),convertFromJsonString(jsonString,type));
    }

    private class RecipeRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private List<Recipe> recipes;

        public RecipeRemoteFactory(@NonNull Context context,
                                   @Nullable List<Recipe> recipes){
            this.context=context;
            this.recipes=recipes;
        }

        @Override
        public void onCreate() {}

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Recipe recipe=at(position);
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_recipe_item);
            remoteViews.setTextViewText(R.id.widget_recipe_title,recipe.getName());
            List<Ingredient> ingredients=recipe.getIngredients();
            if(ingredients!=null){
                StringBuilder builder=new StringBuilder();
                final String BLANK=" ";
                final String bullet="\u25CF";
                for(Ingredient ingredient:ingredients){
                    builder.append(bullet);
                    builder.append(BLANK);
                    builder.append(ingredient.getQuantity());
                    builder.append(BLANK);
                    builder.append(ingredient.getMeasure());
                    builder.append(BLANK);
                    builder.append(ingredient.getIngredient());
                    builder.append('\n');
                    builder.append('\n');
                }
                remoteViews.setTextViewText(R.id.widget_recipe_ingredients,builder.toString());
            }
            return remoteViews;
        }

        private Recipe at(int index){
            return recipes.get(index);
        }

        @Override
        public int getCount() {
            return recipes!=null?recipes.size():0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}

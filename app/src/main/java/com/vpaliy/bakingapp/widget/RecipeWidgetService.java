package com.vpaliy.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.Constants;

import java.util.List;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        RecipeWrapper wrapper=intent.getParcelableExtra(Constants.EXTRA_WIDGET_DATA);
        return new RecipeRemoteFactory(getApplicationContext(),wrapper.getRecipes());
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
        public void onDataSetChanged() {}

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            return null;
        }

        @Override
        public int getCount() {
            return recipes!=null?recipes.size():0;
        }

        @Override
        public long getItemId(int position) {
            return 1;
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

package com.vpaliy.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.WrapperListAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vpaliy.bakingapp.BakingApp;
import com.vpaliy.bakingapp.R;
import com.vpaliy.bakingapp.domain.IRepository;
import com.vpaliy.bakingapp.domain.model.Recipe;
import com.vpaliy.bakingapp.utils.Constants;
import com.vpaliy.bakingapp.utils.scheduler.BaseSchedulerProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.vpaliy.bakingapp.utils.WidgetUtils.convertToJsonString;

public class RecipeWidget extends AppWidgetProvider {

    @Inject
    protected IRepository<Recipe> repository;{
        BakingApp.appInstance()
                .appComponent()
                .inject(this);
    }

    @Inject
    protected BaseSchedulerProvider schedulerProvider;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, List<Recipe> recipes) {
        Intent intent=new Intent(context,RecipeWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        intent.putExtra(Constants.EXTRA_WIDGET_DATA,convertToJsonString(recipes,type));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setRemoteAdapter(R.id.widget_recipe_list,intent);
        views.setEmptyView(R.id.widget_recipe_list,R.id.empty_view);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if(repository!=null){
            repository.getRecipes()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(list->{
                        for(int appWidgetId:appWidgetIds){
                            updateAppWidget(context,appWidgetManager,appWidgetId,list);
                        }
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_recipe_list);
                    });
        }
    }
}


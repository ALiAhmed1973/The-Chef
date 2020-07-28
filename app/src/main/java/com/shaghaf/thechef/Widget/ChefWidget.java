package com.shaghaf.thechef.Widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;

import com.shaghaf.thechef.R;
import com.shaghaf.thechef.model.RecipeIngredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class ChefWidget extends AppWidgetProvider {
    public static final String BUNDLE_EXTRA_INGREDIENTS = "com.shaghaf.thechef.bundle.ingredients";
    public static final String INTENT_EXTRA_INGREDIENTS = "com.shaghaf.thechef.ingredients";
    public static List<RecipeIngredients> recipeIngredientsList;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, List<RecipeIngredients> ingredients) {
        recipeIngredientsList = ingredients;
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;
        if (width < 300) {
            rv = new RemoteViews(context.getPackageName(), R.layout.chef_widget);

            rv.setTextViewText(R.id.appwidget_text, recipeName);
        } else {
            rv = getChefWidgetList(context, ingredients,recipeName);

        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }

    public static void updateChefWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String recipeName,
                                         List<RecipeIngredients> ingredients) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    private static RemoteViews getChefWidgetList(Context context, List<RecipeIngredients> ingredients,String recipeName) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_EXTRA_INGREDIENTS, (ArrayList<? extends Parcelable>) ingredients);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.chef_widget_list);
        remoteViews.setTextViewText(R.id.chef_widget_listView_recipe_name,recipeName);
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(INTENT_EXTRA_INGREDIENTS, bundle);
        remoteViews.setRemoteAdapter(R.id.chef_widget_listView, intent);
        return remoteViews;
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}


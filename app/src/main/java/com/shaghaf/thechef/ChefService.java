package com.shaghaf.thechef;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.shaghaf.thechef.Widget.ChefWidget;
import com.shaghaf.thechef.model.Recipe;
import com.shaghaf.thechef.model.RecipeIngredients;

import java.util.List;

public class ChefService extends IntentService {
    public static final String ACTION_UPDATE_WIDGETS ="com.shaghaf.thechef.action.update_widgets";
    public static final String INTENT_EXTRA_RECIPE ="com.shaghaf.thechef.extra.recipe";

    public static void startActionUpdateWidgets(Context context, Recipe recipe)
    {
        Intent intent = new Intent(context,ChefService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        intent.putExtra(INTENT_EXTRA_RECIPE,recipe);
        context.startService(intent);
    }
    public ChefService() {
        super("ChefService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent!=null)
        {
            final String action = intent.getAction();
            if(ACTION_UPDATE_WIDGETS.equals(action))
            {
                Recipe recipe = intent.getParcelableExtra(INTENT_EXTRA_RECIPE);
                handleActionUpdateWidgets(recipe);

            }

        }

    }

    private void handleActionUpdateWidgets(Recipe recipe) {
        String recipeName = recipe.getName();
        List<RecipeIngredients> ingredients = recipe.getRecipeIngredients();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ChefWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.chef_widget_listView);

        ChefWidget.updateChefWidgets(this,appWidgetManager,appWidgetIds,recipeName,ingredients);

    }
}

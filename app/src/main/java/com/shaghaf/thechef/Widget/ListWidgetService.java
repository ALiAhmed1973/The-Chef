package com.shaghaf.thechef.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shaghaf.thechef.R;
import com.shaghaf.thechef.model.RecipeIngredients;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context context;
        private List<RecipeIngredients> ingredients;
//        Intent mintent;

        private ListRemoteViewsFactory(Context applicationContext,Intent intent) {

           context=applicationContext;
//           mintent=intent;
//            Bundle bundle =  intent.getBundleExtra(ChefWidget.INTENT_EXTRA_INGREDIENTS);
//            if (bundle != null) {
//                ingredients = bundle.getParcelableArrayList(ChefWidget.BUNDLE_EXTRA_INGREDIENTS);
//            }
//            Log.d("ListRemoteViewsFactory", "*******" + ingredients.get(0).getIngredient());
            ingredients=ChefWidget.recipeIngredientsList;


        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredients=ChefWidget.recipeIngredientsList;
//            Bundle bundle = mintent.getBundleExtra(ChefWidget.INTENT_EXTRA_INGREDIENTS);
//            if (bundle != null) {
//                ingredients= bundle.getParcelableArrayList(ChefWidget.BUNDLE_EXTRA_INGREDIENTS);
//            }
//            Log.d("onDataSetChanged", "*******" + ingredients.get(0).getIngredient());
        }

        @Override
        public void onDestroy() {
            if(ingredients!=null) {
                ingredients.clear();
            }
        }

        @Override
        public int getCount() {
            if(ingredients==null)
            {return 0;}
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
          RecipeIngredients recipeIngredient = ingredients.get(i);
            String quantity = String.valueOf(recipeIngredient.getQuantity());
            String measure= recipeIngredient.getMeasure();
            String ingredient= recipeIngredient.getIngredient();
            String allText =quantity+" "+measure+" of "+ingredient;
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.chef_widget);
            remoteViews.setTextViewText(R.id.appwidget_text,allText);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}

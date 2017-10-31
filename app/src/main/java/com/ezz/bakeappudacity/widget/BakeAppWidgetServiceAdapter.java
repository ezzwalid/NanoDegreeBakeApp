package com.ezz.bakeappudacity.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.request.target.AppWidgetTarget;
import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.ingredient.ui.IngredientActivity;
import com.ezz.bakeappudacity.ingredient.ui.fragments.IngredientFragment;
import com.ezz.bakeappudacity.recipe.model.Ingredient;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.RecipeActivity;
import com.ezz.bakeappudacity.recipe.ui.fragments.RecipeFragment;
import com.ezz.bakeappudacity.steps.StepsActivity;
import com.ezz.bakeappudacity.steps.ui.fragments.StepsFragment;

import java.util.ArrayList;



/**
 * Created by EzzWalid on 10/29/2017.
 */

public class BakeAppWidgetServiceAdapter implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    ArrayList<Ingredient> ingredients;

    public BakeAppWidgetServiceAdapter(Context context) {
        this.context = context;
        if (StepsActivity.recipe != null){
            ingredients = StepsActivity.recipe.getIngredients();
        }
        else {
            ingredients =new ArrayList<>();
        }
    }

    @Override
    public void onCreate() {

    }



    @Override
    public void onDataSetChanged() {
        if (StepsActivity.recipe != null){
            ingredients = StepsActivity.recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_item);
        remoteViews.setTextViewText(R.id.widgetTitle, ingredients.get(i).getIngredient());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IngredientFragment.INGREDIENTS_KEY, ingredients);
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.widgetTitle, intent);
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
        return false;
    }

    public static class Service extends RemoteViewsService {
        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new BakeAppWidgetServiceAdapter(this);
        }
    }
}

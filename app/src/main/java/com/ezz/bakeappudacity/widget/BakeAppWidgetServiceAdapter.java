package com.ezz.bakeappudacity.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.request.target.AppWidgetTarget;
import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.RecipeActivity;
import com.ezz.bakeappudacity.recipe.ui.fragments.RecipeFragment;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by EzzWalid on 10/29/2017.
 */

public class BakeAppWidgetServiceAdapter implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    ArrayList<Recipe> recipes;

    public BakeAppWidgetServiceAdapter(Context context) {
        this.context = context;
        if (RecipeFragment.recipes != null){
            recipes = RecipeFragment.recipes;
        }
        else {
            recipes =new ArrayList<>();
        }
    }

    @Override
    public void onCreate() {

    }



    @Override
    public void onDataSetChanged() {
        if (RecipeFragment.recipes != null){
            recipes = RecipeFragment.recipes;
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_item);
        remoteViews.setTextViewText(R.id.widgetTitle, recipes.get(i).getName());
        remoteViews.setImageViewResource(R.id.widgetImage, R.mipmap.widget_thumb);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeFragment.RECIPES_KEY, recipes.get(i));
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.widgetImage, intent);
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

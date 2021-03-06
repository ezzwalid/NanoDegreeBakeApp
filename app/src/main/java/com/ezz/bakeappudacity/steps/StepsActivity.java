package com.ezz.bakeappudacity.steps;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.baseListners.RecyclerViewClickListener;
import com.ezz.bakeappudacity.helpers.Utils;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.fragments.RecipeFragment;
import com.ezz.bakeappudacity.steps.ui.fragments.StepDetailsFragment;
import com.ezz.bakeappudacity.steps.ui.fragments.StepsFragment;
import com.ezz.bakeappudacity.widget.BakeAppWidget;

public class StepsActivity extends AppCompatActivity implements RecyclerViewClickListener{

    public static Recipe recipe;
    StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recipe = getIntent().getParcelableExtra(RecipeFragment.RECIPES_KEY);
        addFragments(savedInstanceState);
        updateWidgetService();
    }

    private void addFragments(Bundle bundle){
        if (bundle == null){
            if (Utils.isTablet(this)){
                recipe.getSteps().get(0).setSelected(true);
                stepDetailsFragment = (StepDetailsFragment) StepDetailsFragment.newInstance(recipe.getSteps(),0);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.steps_frag_container, StepsFragment.newInstance(recipe));
                fragmentTransaction.replace(R.id.steps_details_fragment_container, stepDetailsFragment, "detailsFragment");
                fragmentTransaction.commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.steps_frag_container, StepsFragment.newInstance(recipe))
                        .commit();
            }
        }
        else if (Utils.isTablet(this)){
            stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag("detailsFragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeFragment.RECIPES_KEY, recipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipe = savedInstanceState.getParcelable(RecipeFragment.RECIPES_KEY);
    }

    @Override
    public void onRecyclerItemClicked(Object o, int position) {
        if (Utils.isTablet(this)){
            stepDetailsFragment.scrollToPage(position, false);
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_frag_container, StepDetailsFragment.newInstance(recipe.getSteps(), position))
                    .addToBackStack(null)
                    .commit();
        }
    }
    private void updateWidgetService(){
        Intent intent = new Intent(this, BakeAppWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
        }
    }
    //===================================================================
}

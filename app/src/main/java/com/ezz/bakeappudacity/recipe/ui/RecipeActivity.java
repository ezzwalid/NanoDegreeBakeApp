package com.ezz.bakeappudacity.recipe.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.baseListners.IdealListener;
import com.ezz.bakeappudacity.helpers.TestingIdlingResource;

public class RecipeActivity extends AppCompatActivity implements IdealListener{


    @Nullable
    private TestingIdlingResource testingIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (testingIdlingResource == null) {
            testingIdlingResource = new TestingIdlingResource();
        }
        return testingIdlingResource;
    }

    @Override
    public void onIdeal() {
        if (testingIdlingResource != null) {
            testingIdlingResource.setIdleState(true);
        }
    }
}

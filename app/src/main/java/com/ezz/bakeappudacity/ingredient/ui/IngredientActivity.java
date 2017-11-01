package com.ezz.bakeappudacity.ingredient.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.ingredient.ui.fragments.IngredientFragment;
import com.ezz.bakeappudacity.recipe.model.Ingredient;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {

    private final String INGREDIENT_FRAGMENT_KEY = "ingredientFragmentKey";

    ArrayList<Ingredient> ingredients;
    IngredientFragment ingredientFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ingredients = getIntent().getParcelableArrayListExtra(IngredientFragment.INGREDIENTS_KEY);
        addIngredientFragment(savedInstanceState);
    }

    public void addIngredientFragment(Bundle bundle){
        if (bundle != null){
            ingredientFragment = (IngredientFragment) getSupportFragmentManager().getFragment(bundle, INGREDIENT_FRAGMENT_KEY);
        }
        else {
                ingredientFragment = (IngredientFragment) IngredientFragment.newInstance(ingredients);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.ingredient_frag_container, ingredientFragment)
        .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(IngredientFragment.INGREDIENTS_KEY, ingredients);
        getSupportFragmentManager().putFragment(outState, INGREDIENT_FRAGMENT_KEY, ingredientFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ingredients = savedInstanceState.getParcelableArrayList(IngredientFragment.INGREDIENTS_KEY);
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
}

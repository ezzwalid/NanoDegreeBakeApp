package com.ezz.bakeappudacity.recipe.ui.fragments;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.BaseFragment;
import com.ezz.bakeappudacity.base.baseListners.IdealListener;
import com.ezz.bakeappudacity.base.baseListners.RecyclerViewClickListener;
import com.ezz.bakeappudacity.helpers.TestingIdlingResource;
import com.ezz.bakeappudacity.helpers.Utils;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.adapters.RecipeAdapter;
import com.ezz.bakeappudacity.recipe.ui.presenters.RecipePresenter;
import com.ezz.bakeappudacity.recipe.ui.presenters.impl.RecipePresenterImpl;
import com.ezz.bakeappudacity.recipe.ui.views.RecipeView;
import com.ezz.bakeappudacity.steps.StepsActivity;
import com.ezz.bakeappudacity.widget.BakeAppWidget;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends BaseFragment<RecipePresenter> implements RecipeView, RecyclerViewClickListener{
    //===================================================================
    RecipeAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    //===================================================================
    @BindView(R.id.recipe_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recipe_recycler_view_progress)
    ProgressBar progressBar;
    //===================================================================
    private final int RECIPES_REQUEST_CODE = 1;
    //===================================================================
    public static final String RECIPES_KEY = "recipeKey";
    public static ArrayList<Recipe> recipes;
    //===================================================================
    public RecipeFragment() {
        // Required empty public constructor
    }
    //===================================================================
    @Override
    protected RecipePresenter createPresenter() {
        return new RecipePresenterImpl();
    }
    //===================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }
    //===================================================================
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        handleSavedInstanceState(savedInstanceState);
        initRecycler();
        getRecipes();
    }
    //===================================================================
    //===================================================================
    private void initRecycler(){
        initAdapter();
        initLayoutManger();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    //===================================================================
    private void initAdapter(){
        if (adapter == null){
            if (recipes != null){
                adapter = new RecipeAdapter(recipes);
            }
            else {
                adapter = new RecipeAdapter(new ArrayList<Recipe>());
            }
            adapter.setClickListener(this);
        }
    }
    //===================================================================
    private void initLayoutManger(){
        if (layoutManager == null){
            if (Utils.isTablet(getContext())){
                layoutManager = new GridLayoutManager(getContext(), 3);
            }
            else {
                layoutManager = new LinearLayoutManager(getContext());
            }
        }
    }
    //===================================================================
    //===================================================================
    private void getRecipes(){
        if (recipes == null){
            getPresenter().getRecipes(RECIPES_REQUEST_CODE);
        }
    }
    //===================================================================
    @Override
    public void showLoading(int requestCode) {
        if (requestCode == RECIPES_REQUEST_CODE){
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    //===================================================================
    @Override
    public void hideLoading(int requestCode) {
        if (requestCode == RECIPES_REQUEST_CODE){
            progressBar.setVisibility(View.GONE);
        }
    }
    //===================================================================
    @Override
    public void populateRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        adapter.updateData(recipes);
        updateWidgetService();
        ((IdealListener)getActivity()).onIdeal();
    }
    private void updateWidgetService(){
        Intent intent = new Intent(getActivity(), BakeAppWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
        }
    }
    //===================================================================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_KEY, recipes);
    }
    //===================================================================
    private void handleSavedInstanceState(Bundle bundle){
        if (bundle != null){
            recipes = bundle.getParcelableArrayList(RECIPES_KEY);
        }
    }
    //===================================================================
    @Override
    public void onRecyclerItemClicked(Object o, int position) {
        Intent intent = new Intent(getContext(), StepsActivity.class);
        intent.putExtra(RECIPES_KEY, (Recipe) o);
        getActivity().startActivity(intent);
    }
    //===================================================================

}

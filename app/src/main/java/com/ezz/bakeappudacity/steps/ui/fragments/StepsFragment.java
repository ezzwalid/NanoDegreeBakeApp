package com.ezz.bakeappudacity.steps.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.base.baseListners.RecyclerViewClickListener;
import com.ezz.bakeappudacity.helpers.Utils;
import com.ezz.bakeappudacity.ingredient.ui.IngredientActivity;
import com.ezz.bakeappudacity.ingredient.ui.fragments.IngredientFragment;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.fragments.RecipeFragment;
import com.ezz.bakeappudacity.steps.ui.adapters.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment{
    //===================================================================
    Recipe recipe;
    //===================================================================
    StepsAdapter adapter;
    //===================================================================
    public static final Fragment newInstance(Recipe recipe){
        StepsFragment fragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeFragment.RECIPES_KEY, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }
    //===================================================================
    @BindView(R.id.ingredient_btn)
    Button ingredientBtn;
    @BindView(R.id.steps_recycler_view)
    RecyclerView recyclerView;
    //===================================================================
    public StepsFragment() {
        // Required empty public constructor
    }
    //===================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }
    //===================================================================
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recipe = getArguments().getParcelable(RecipeFragment.RECIPES_KEY);
        handleSavedInstanceState(savedInstanceState);
        setBtnsOnClick();
        initRecycler();
    }
    //===================================================================
    private void setBtnsOnClick(){
        ingredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), IngredientActivity.class);
                intent.putParcelableArrayListExtra(IngredientFragment.INGREDIENTS_KEY, recipe.getIngredients());
                getActivity().startActivity(intent);
            }
        });
    }
    //===================================================================
    private void initRecycler(){
        initAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    //===================================================================
    private void initAdapter(){
        if (adapter == null){
            adapter = new StepsAdapter(Utils.isTablet(getContext()), recipe.getSteps());
            adapter.setClickListener((RecyclerViewClickListener) getActivity());
        }
    }
    //===================================================================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeFragment.RECIPES_KEY, recipe);
    }
    //===================================================================
    private void handleSavedInstanceState(Bundle bundle){
        if (bundle != null){
            recipe = bundle.getParcelable(RecipeFragment.RECIPES_KEY);
        }
    }
    //===================================================================
}

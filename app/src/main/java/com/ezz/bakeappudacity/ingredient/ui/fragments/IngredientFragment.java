package com.ezz.bakeappudacity.ingredient.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.helpers.Utils;
import com.ezz.bakeappudacity.ingredient.ui.adapters.IngredientAdapter;
import com.ezz.bakeappudacity.recipe.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {
    //===================================================================
    public static final String INGREDIENTS_KEY = "ingredientKey";
    ArrayList<Ingredient> ingredients;
    //===================================================================
    IngredientAdapter adapter;
    //===================================================================
    @BindView(R.id.ingredient_recyclerView)
    RecyclerView recyclerView;
    //===================================================================
    public static Fragment newInstance(ArrayList<Ingredient> ingredients){
        IngredientFragment fragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS_KEY, ingredients);
        fragment.setArguments(bundle);
        return fragment;
    }
    //===================================================================
    public IngredientFragment() {
        // Required empty public constructor
    }
    //===================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredeint, container, false);
    }
    //===================================================================
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ingredients = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
        handleSavedInstanceState(savedInstanceState);
        initRecycler();
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
            adapter = new IngredientAdapter(false, ingredients);
        }
    }
    //===================================================================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENTS_KEY, ingredients);
    }
    //===================================================================
    private void handleSavedInstanceState(Bundle bundle){
        if (bundle != null){
            ingredients = bundle.getParcelableArrayList(INGREDIENTS_KEY);
        }
    }
}

package com.ezz.bakeappudacity.recipe.ui.views;

import com.ezz.bakeappudacity.base.view.BaseView;
import com.ezz.bakeappudacity.recipe.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public interface RecipeView extends BaseView {
    //===================================================================
    void populateRecipes(ArrayList<Recipe> recipes);
    //===================================================================
}

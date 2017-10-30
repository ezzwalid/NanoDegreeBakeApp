package com.ezz.bakeappudacity.recipe.ui.presenters;

import com.ezz.bakeappudacity.base.BasePresenter;
import com.ezz.bakeappudacity.recipe.ui.views.RecipeView;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public abstract class RecipePresenter extends BasePresenter<RecipeView> {
    //===================================================================
    public abstract void getRecipes(int requestCode);
    //===================================================================
}

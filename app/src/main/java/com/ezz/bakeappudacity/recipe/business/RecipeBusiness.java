package com.ezz.bakeappudacity.recipe.business;


import com.ezz.bakeappudacity.base.exception.UiException;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.model.cloud.RecipeClodDataManger;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by EzzWalid on 10/20/2017.
 */

public class RecipeBusiness {
    //===================================================================
    RecipeClodDataManger clodDataManger = new RecipeClodDataManger();
    //===================================================================
    public ArrayList<Recipe> getRecipes() throws UiException {
        return clodDataManger.getRecipes();
    }
    //===================================================================

}

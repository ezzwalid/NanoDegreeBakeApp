package com.ezz.bakeappudacity.recipe.model.cloud;

import com.ezz.bakeappudacity.base.datamanager.BaseCloudDataManager;
import com.ezz.bakeappudacity.base.exception.UiException;
import com.ezz.bakeappudacity.recipe.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public class RecipeClodDataManger extends BaseCloudDataManager {
    //===================================================================
    public ArrayList<Recipe> getRecipes() throws UiException {
        return performWebCall(create(RecipeCloudServices.class).requestRecipes());
    }
    //===================================================================
}

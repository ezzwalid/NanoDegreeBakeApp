package com.ezz.bakeappudacity.recipe.model.cloud;

import com.ezz.bakeappudacity.recipe.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by EzzWalid on 10/19/2017.
 */

public interface RecipeCloudServices {
    //===================================================================
    @GET("baking.json")
    Call<ArrayList<Recipe>> requestRecipes();
    //===================================================================
}

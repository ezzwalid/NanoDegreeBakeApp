package com.ezz.bakeappudacity.recipe.ui.presenters.impl;


import com.ezz.bakeappudacity.base.baseListners.Request_fail_dialog_action_listener;
import com.ezz.bakeappudacity.base.datamanager.interfaces.AsyncAction;
import com.ezz.bakeappudacity.base.datamanager.interfaces.OnRequestCompletedListener;
import com.ezz.bakeappudacity.base.exception.UiException;
import com.ezz.bakeappudacity.recipe.business.RecipeBusiness;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.presenters.RecipePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public class RecipePresenterImpl extends RecipePresenter {
    //===================================================================
    RecipeBusiness recipeBusiness = new RecipeBusiness();
    //===================================================================
    @Override
    public void getRecipes(final int requestCode) {
        getView().showLoading(requestCode);

        final AsyncAction asyncAction = new AsyncAction() {
            @Override
            public Object run() throws UiException {
                return recipeBusiness.getRecipes();
            }
        };




        OnRequestCompletedListener onRequestCompletedListener = new OnRequestCompletedListener() {
            @Override
            public void onSuccess(Object response) {
                getView().hideLoading(requestCode);
                getView().populateRecipes((ArrayList<Recipe>) response);
            }

            @Override
            public void onFail(Exception ex) {
                getView().handleConnectionError(new Request_fail_dialog_action_listener() {
                    @Override
                    public void positiveAction() {
                        getRecipes(requestCode);
                    }
                });
            }
        };

        performAsyncOperation(asyncAction, onRequestCompletedListener);

    }
    //===================================================================
}

package com.ezz.bakeappudacity.steps.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ezz.bakeappudacity.recipe.model.Step;
import com.ezz.bakeappudacity.steps.ui.fragments.StepDetailsPagerFragment;

import java.util.ArrayList;

/**
 * Created by EzzWalid on 10/27/2017.
 */

public class StepPagerFragmentHolder {
    private ArrayList<Step> steps;
    private Bundle savedInstanceState;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> pagerFragments;

    public StepPagerFragmentHolder(ArrayList<Step> steps, Bundle savedInstanceState, FragmentManager fragmentManager) {
        this.steps = steps;
        this.savedInstanceState = savedInstanceState;
        this.fragmentManager = fragmentManager;
        initFragments();
    }

    public ArrayList<Fragment> getPagerFragments() {
       return this.pagerFragments;
    }

    public void setPagerFragments(ArrayList<Fragment> pagerFragments) {
        this.pagerFragments = pagerFragments;
    }

    private void initFragments(){
        pagerFragments = new ArrayList<>();
        if (savedInstanceState != null){
            for (Step step : steps){
                pagerFragments.add(fragmentManager.getFragment(savedInstanceState, step.getId().toString()));
            }
        }
        else {
            for (Step step : steps){
                pagerFragments.add(StepDetailsPagerFragment.newInstance(step));
            }
        }
    }

    public void saveState(Bundle outState){
        for (int i = 0 ; i < pagerFragments.size() ; i++){
            fragmentManager.putFragment(outState, steps.get(i).getId().toString(), pagerFragments.get(i));
        }
    }

}

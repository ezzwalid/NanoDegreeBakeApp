package com.ezz.bakeappudacity.steps.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ezz.bakeappudacity.recipe.model.Step;
import com.ezz.bakeappudacity.steps.ui.fragments.StepDetailsPagerFragment;

import java.util.ArrayList;

/**
 * Created by EzzWalid on 10/27/2017.
 */

public class StepPagerAdapter extends FragmentStatePagerAdapter{

    ArrayList<Step> steps;

    public StepPagerAdapter(FragmentManager fm, ArrayList<Step> steps) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        return StepDetailsPagerFragment.newInstance(steps.get(position));
    }

    @Override
    public int getCount() {
        return steps.size();
    }
}

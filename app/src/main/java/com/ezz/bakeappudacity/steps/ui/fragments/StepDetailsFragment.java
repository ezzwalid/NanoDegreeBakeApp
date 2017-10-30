package com.ezz.bakeappudacity.steps.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.recipe.model.Step;
import com.ezz.bakeappudacity.steps.ui.adapters.StepPagerAdapter;
import com.ezz.bakeappudacity.steps.ui.adapters.StepPagerFragmentHolder;
import com.ezz.bakeappudacity.steps.ui.listener.ControlStepPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment implements ControlStepPager{

    StepPagerAdapter adapter;

    public static final String PAGER_SELECTED_PAGE_KEY = "PAGERSELECTEDPAGE";
    int pagerSelectedPage;

    public static final String STEPS_KEY = "stepsKey";
    ArrayList<Step> steps;

    StepPagerFragmentHolder pagerFragmentHolder;

    @BindView(R.id.steps_view_pager)
    ViewPager viewPager;

    public static Fragment newInstance(ArrayList<Step> steps, int pagerSelectedPage){
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_KEY, steps);
        bundle.putInt(PAGER_SELECTED_PAGE_KEY, pagerSelectedPage);
        fragment.setArguments(bundle);
        return fragment;
    }

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = getArguments().getParcelableArrayList(STEPS_KEY);
        pagerSelectedPage = getArguments().getInt(PAGER_SELECTED_PAGE_KEY);
        restoreState(savedInstanceState);
        if (adapter == null){
            adapter = new StepPagerAdapter(getChildFragmentManager(), steps);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initPager();
    }


    private void initPager(){
        /*
           initPagerFragmentHolder(bundle);
        if (adapter == null){
            adapter = new StepPagerAdapter(getChildFragmentManager(), pagerFragmentHolder.getPagerFragments());
            adapter = new StepPagerAdapter(getChildFragmentManager(), steps);
        }
         */

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pagerSelectedPage, false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerSelectedPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPagerFragmentHolder(Bundle bundle){
        if (pagerFragmentHolder == null){
            pagerFragmentHolder = new StepPagerFragmentHolder(steps, bundle, getFragmentManager());
        }
    }

    private void restoreState(Bundle bundle){
        if (bundle != null){
            steps = bundle.getParcelableArrayList(STEPS_KEY);
            pagerSelectedPage = bundle.getInt(PAGER_SELECTED_PAGE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEPS_KEY, steps);
        outState.putInt(PAGER_SELECTED_PAGE_KEY, pagerSelectedPage);
      //  pagerFragmentHolder.saveState(outState);
    }

    @Override
    public void scrollToNextPage() {
        int itemsCount = adapter.getCount();
        int currentItemPosition = viewPager.getCurrentItem();
        if (currentItemPosition + 1 < itemsCount) {
            viewPager.setCurrentItem(currentItemPosition +1, true);
        }
        else {
            Toast.makeText(getContext(), "Last item", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void scrollToPreviousPage() {
        int currentItemPosition = viewPager.getCurrentItem();
        if (currentItemPosition - 1 >= 0) {
            viewPager.setCurrentItem(currentItemPosition -1, true);
        }
        else {
            Toast.makeText(getContext(), "First item", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void scrollToPage(int page, boolean smooth) {
        viewPager.setCurrentItem(page, smooth);
    }
}

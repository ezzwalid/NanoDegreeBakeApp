package com.ezz.bakeappudacity.steps.ui.listener;

/**
 * Created by EzzWalid on 10/28/2017.
 */

public interface ControlStepPager {
    void scrollToNextPage();
    void scrollToPreviousPage();
    void scrollToPage(int page, boolean smooth);
}

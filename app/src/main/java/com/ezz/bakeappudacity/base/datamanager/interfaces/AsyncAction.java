package com.ezz.bakeappudacity.base.datamanager.interfaces;


import com.ezz.bakeappudacity.base.exception.UiException;

/**
 * Created by EzzWalid on 9/21/2017.
 */


public interface AsyncAction <T> {
    Object run() throws UiException;
}//end AsyncAction

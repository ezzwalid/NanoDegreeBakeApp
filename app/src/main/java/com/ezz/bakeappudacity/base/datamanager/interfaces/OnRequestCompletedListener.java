package com.ezz.bakeappudacity.base.datamanager.interfaces;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public interface OnRequestCompletedListener {

    public void onSuccess(Object response);

    public void onFail(Exception ex);


}//end OnRequestCompletedListener

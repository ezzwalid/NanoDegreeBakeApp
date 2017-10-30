package com.ezz.bakeappudacity.base.datamanager;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public abstract class BaseCloudResponse extends StatusOnlyResponse {
    //===================================================================
    private  Object data;
    //===================================================================
    public void setData(Object data) {
        this.data = data;
    }
    //===================================================================
    public abstract Object getData();
}//end BaseCloudResponse

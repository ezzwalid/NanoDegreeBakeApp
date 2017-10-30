package com.ezz.bakeappudacity.base;

import android.os.AsyncTask;

import com.ezz.bakeappudacity.base.datamanager.interfaces.AsyncAction;
import com.ezz.bakeappudacity.base.datamanager.interfaces.OnRequestCompletedListener;


/**
 * Created by EzzWalid on 9/21/2017.
 */

public class AsyncPerformer extends AsyncTask{
    //===================================================================
    private final AsyncAction actionToPerform;
    private final OnRequestCompletedListener callback;
    //===================================================================
    public AsyncPerformer(AsyncAction actionToPerform, OnRequestCompletedListener callback){
        this.actionToPerform = actionToPerform;
        this.callback = callback;
    }//end constructor
    //===================================================================
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            return actionToPerform.run();
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }//end doInBackground
    //===================================================================
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if(result instanceof Exception) {
            callback.onFail((Exception) result);
            return;
        }

        callback.onSuccess(result);
    }
    //===================================================================
}//end AsyncPerformer

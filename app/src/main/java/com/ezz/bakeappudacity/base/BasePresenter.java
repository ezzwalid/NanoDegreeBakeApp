package com.ezz.bakeappudacity.base;


import com.ezz.bakeappudacity.base.datamanager.interfaces.AsyncAction;
import com.ezz.bakeappudacity.base.datamanager.interfaces.OnRequestCompletedListener;
import com.ezz.bakeappudacity.base.view.BaseView;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public abstract class BasePresenter<V extends BaseView> {
    //===================================================================
    AsyncQueue asyncQueue;
    //===================================================================
    private V curView;
    //===================================================================
    public void onAttach(V view){
        curView = view;
        asyncQueue = new AsyncQueue();
    }
    //===================================================================
    protected V getView(){
        return curView;
    }
    //===================================================================
    protected void performAsyncOperation(AsyncAction actionToPerform,
                                         OnRequestCompletedListener callback){
        AsyncPerformer performer = new AsyncPerformer(actionToPerform, callback);
        performer.execute();
        asyncQueue.addTask(performer);
    }//end performAsyncOperation
    //===================================================================
    public void onDetach(){
        if (curView != null)
            curView = null;
        if (asyncQueue != null) {
            asyncQueue.stopAllTasks();
            asyncQueue = null;
        }
    }
    //===================================================================
    //===================================================================
}//end BasePresenter

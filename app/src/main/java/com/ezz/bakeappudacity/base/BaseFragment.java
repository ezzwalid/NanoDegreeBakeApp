package com.ezz.bakeappudacity.base;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ezz.bakeappudacity.base.baseListners.Request_fail_dialog_action_listener;
import com.ezz.bakeappudacity.base.view.BaseView;


/**
 * Created by EzzWalid on 9/21/2017.
 */

public abstract class BaseFragment<T extends BasePresenter>  extends Fragment implements BaseView {
    //===================================================================
    private T presenter;
    //===================================================================
    protected abstract  T createPresenter();
    //===================================================================
    protected @NonNull
    T getPresenter(){
        if(presenter == null)
            presenter = createPresenter();

        if(presenter == null)
            throw new IllegalStateException("UnitTypePresenter can not be null!");

        return presenter;
    }//end getPresenter
    //===================================================================
    public String getTitle(){
        return getClass().getName();
    }//end getTitle
    //===================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onAttach(this);
    }//end onCreate
    //===================================================================
    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDetach();
    }
    //===================================================================
    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }//end showError
    //===================================================================
    @Override
    public Activity getRunningActivity() {
        return getActivity();
    }
    //===================================================================
    @Override
    public void showError(int errorMsg) {
        Toast.makeText(getContext(), getString(errorMsg), Toast.LENGTH_SHORT).show();
    }//end showError
    //===================================================================
    @Override
    public void handleConnectionError(final Request_fail_dialog_action_listener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert!");
        builder.setMessage("Somthing went wrong, please cheack your internet connction then press retry.");
        builder.setPositiveButton("retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.positiveAction();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //===================================================================
}//end BaseFragment

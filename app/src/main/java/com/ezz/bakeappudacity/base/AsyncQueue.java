package com.ezz.bakeappudacity.base;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by EzzWalid on 9/21/2017.
 */


public class AsyncQueue {
    ArrayList<AsyncTask> asyncTasks = new ArrayList<>();

    public void addTask(AsyncTask task){
        asyncTasks.add(task);
    }


    public void stopTask(AsyncTask task){
        if (task != null) {
            if (asyncTasks.contains(task)) {
                task.cancel(true);
                task = null;
                asyncTasks.remove(task);
            }
        }
    }

    public void stopAllTasks(){
        for (int i = 0 ; i < asyncTasks.size() ; i++){
            AsyncTask task = asyncTasks.get(i);
            if (task != null){
                task.cancel(true);
                task = null;
                asyncTasks.remove(task);
            }
        }
    }
}

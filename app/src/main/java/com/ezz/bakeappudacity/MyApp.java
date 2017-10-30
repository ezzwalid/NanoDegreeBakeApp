package com.ezz.bakeappudacity;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by EzzWalid on 10/29/2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

package com.ezz.bakeappudacity.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;

import com.ezz.bakeappudacity.R;

/**
 * Created by EzzWalid on 10/20/2017.
 */

public class Utils {
    public static boolean isTablet(Context context){
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static boolean isLandscape(Context context){
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

package com.ezz.bakeappudacity.base.datamanager;


import android.util.Log;


import com.ezz.bakeappudacity.base.exception.UiException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class BaseCloudDataManager {
    //===================================================================
    private final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private final String LOG_TAG = "BaseCloudDataManager";
    //--------------------------------------------------------------------
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //===================================================================
    protected <SC> SC create(Class<SC> serviceClass){
       return retrofit.create(serviceClass);
    }
    //===================================================================
    protected <T> T performWebCall(Call<T> retrofitCall) throws UiException {
        try {
            Response<T>  response = retrofitCall.execute();
            Log.d(LOG_TAG, "response = " + response.body().toString());

            String res = response.body().toString();
            if(response.isSuccessful())
                return response.body();

            Log.e("", response.errorBody().string());
            throw new UiException(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
            throw new UiException(e);
        }
    }//end performWebCall
    //===================================================================
}//end BaseCloudDataManager

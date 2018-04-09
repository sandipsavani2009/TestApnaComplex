package com.test.apnacomplex;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.test.apnacomplex.io.retrofit.RetrofitIoHelper;

/**
 * Created by sc on 5/4/18.
 */

public class ApnaComplexApplication extends Application {

    private static ApnaComplexApplication sInstance;

    public synchronized static ApnaComplexApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        sInstance = ApnaComplexApplication.this;
    }

    /**
     * Checks for internet connection
     * @return - true if net connection available , false otherwise
     */
    public boolean hasNetworkConnection() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}

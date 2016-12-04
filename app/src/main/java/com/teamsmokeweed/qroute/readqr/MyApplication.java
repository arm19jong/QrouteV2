package com.teamsmokeweed.qroute.readqr;

import android.app.Application;
import android.content.Context;

import com.teamsmokeweed.qroute.Content;

/**
 * Created by jongzazaal on 4/12/2559.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        if (mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public Context gContext(){
        return MyApplication.this;
    }
}
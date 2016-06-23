package com.example.ishant.jsonblob;

import android.app.Application;

import java.util.Map;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class MyApplication extends Application {

    private static MyApplication singleInstance = null;

    public static MyApplication getInstance() {
        return singleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
    }

}

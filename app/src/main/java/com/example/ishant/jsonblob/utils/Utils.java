package com.example.ishant.jsonblob.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ishant.jsonblob.MyApplication;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class Utils {
    private static final Context context;

    static {
        context = MyApplication.getInstance().getApplicationContext();
    }

    public static boolean isInternetConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}

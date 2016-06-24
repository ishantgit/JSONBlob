package com.example.ishant.jsonblob;

import android.app.Application;
import android.content.ComponentName;
import android.content.pm.PackageManager;

import com.example.ishant.jsonblob.receivers.TaskListener;

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

    public static void enableReceiver(){
        ComponentName component = new ComponentName(singleInstance, TaskListener.class);
        singleInstance.getPackageManager().setComponentEnabledSetting(component, PackageManager. COMPONENT_ENABLED_STATE_ENABLED , PackageManager.DONT_KILL_APP);
    }

    public static void disableReceiver(){
        ComponentName component = new ComponentName(singleInstance, TaskListener.class);
        singleInstance.getPackageManager().setComponentEnabledSetting(component, PackageManager. COMPONENT_ENABLED_STATE_DISABLED , PackageManager.DONT_KILL_APP);
    }


}

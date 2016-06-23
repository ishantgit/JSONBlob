package com.example.ishant.jsonblob.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ishant Rana on 24/06/16.
 */
public class TaskListener extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;


    public static OnPostTokenReceivedListener onPostTokenReceivedListener;

    public interface OnPostTokenReceivedListener {
        void onTokenReceived();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onPostTokenReceivedListener.onTokenReceived();
    }
}

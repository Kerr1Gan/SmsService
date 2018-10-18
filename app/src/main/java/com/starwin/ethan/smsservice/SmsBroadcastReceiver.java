package com.starwin.ethan.smsservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i(TAG, String.valueOf(isOrderedBroadcast()));
        Log.i(TAG, "SmsBroadcastReceiver begin");
        Intent serviceIntent = new Intent(context, SmsService.class);
        serviceIntent.putExtra("intent", intent);
        context.startService(serviceIntent);
        Log.i(TAG, "SmsBroadcastReceiver end");
    }
}

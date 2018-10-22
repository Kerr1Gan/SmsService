package com.starwin.ethan.smsservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i(TAG, String.valueOf(isOrderedBroadcast()));
        Log.i(TAG, "SmsBroadcastReceiver begin");
        SharedPreferences preferences = context.getSharedPreferences("pref", Context.MODE_MULTI_PROCESS);
        String phone = preferences.getString("self_phone", null);
        Intent serviceIntent = new Intent(context, SmsService.class);
        serviceIntent.putExtra("intent", intent);
        serviceIntent.putExtra(SmsService.EXTRA_SELF_PHONE, phone);
        context.startService(serviceIntent);
        Log.i(TAG, "SmsBroadcastReceiver end");
    }
}

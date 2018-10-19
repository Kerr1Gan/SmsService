package com.starwin.ethan.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.SmsService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainPresenter implements MainContract.Presenter {

    private static final int REQUEST_CODE = 100;

    private MainContract.View mView;

    private AppExecutors mAppExecutors;

    private SmsDatabase mSmsDatabase;

    private Context mContext;

    private BroadcastReceiver mReceiver;

    @Inject
    public MainPresenter(Context context) {
        mContext = context;
        IntentFilter intentFilter = new IntentFilter(SmsService.ACTION_SMS_SERVICE_RECEIVED_MSG);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshSms();
            }
        };
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void takeView(MainContract.View view) {
        mView = view;
        refreshSms();
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void refreshSms() {
        if (mView != null) {
            mAppExecutors.networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    final List<SmsMessage> messageList = mSmsDatabase.smsDao().getAllSms();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            mView.notifySmsList(messageList);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void init(SmsDatabase database, AppExecutors executors) {
        mSmsDatabase = database;
        mAppExecutors = executors;
        if (ActivityCompat.checkSelfPermission(mContext, "android.permission.RECEIVE_SMS") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{"android.permission.RECEIVE_SMS"}, REQUEST_CODE);
        }
    }

    @Override
    public void destroy() {
        mSmsDatabase.close();
        mContext.unregisterReceiver(mReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals("android.permission.RECEIVE_SMS")) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(mContext, "We need read sms permission", Toast.LENGTH_LONG).show();
                        go2Setting();
                    }
                }
            }
        }
    }

    private void go2Setting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
    }
}

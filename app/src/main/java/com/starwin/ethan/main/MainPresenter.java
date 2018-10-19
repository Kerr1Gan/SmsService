package com.starwin.ethan.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.SmsService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainPresenter implements MainContract.Presenter {

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
    }

    @Override
    public void destroy() {
        mSmsDatabase.close();
        mContext.unregisterReceiver(mReceiver);
    }
}

package com.starwin.ethan.main;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private AppExecutors mAppExecutors;

    private SmsDatabase mSmsDatabase;

    @Inject
    public MainPresenter() {
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

    public void destroy() {
        mSmsDatabase.close();
    }
}

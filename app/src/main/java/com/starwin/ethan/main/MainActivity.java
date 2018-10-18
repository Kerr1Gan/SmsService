package com.starwin.ethan.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainActivity extends DaggerMvpActivity<MainComponent, MainActivity> implements MainContract.View {

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    AppExecutors mAppExecutors;

    @Inject
    SmsDatabase mSmsDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.takeView(this);
        mAppExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SmsMessage> messageList = mSmsDatabase.smsDao().getAllSms();
                mSmsDatabase.close();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.dropView();
    }

    @Override
    protected void onDestroy() {
        mSmsDatabase.close();
        super.onDestroy();
    }

    @Override
    protected MainComponent initDaggerComponent() {
        return DaggerMainComponent.builder().application(this.getApplication()).build();
    }

    @Override
    protected MainActivity getComponentInject() {
        return this;
    }
}

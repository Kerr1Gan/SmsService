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
        mMainPresenter.init(mSmsDatabase, mAppExecutors);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.dropView();
    }

    @Override
    protected void onDestroy() {
        mMainPresenter.destroy();
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


    @Override
    public void notifySmsList(List<SmsMessage> messages) {
        int x=0;
        x++;
    }
}

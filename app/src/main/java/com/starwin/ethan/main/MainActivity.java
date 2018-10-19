package com.starwin.ethan.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.starwin.ethan.adapter.SmsListAdapter;
import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.R;

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

    private List<SmsMessage> mMessageList;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter.init(mSmsDatabase, mAppExecutors);
        mListView = findViewById(R.id.list_view);
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
        if (mMessageList == null) {
            mMessageList = messages;
            mListView.setAdapter(new SmsListAdapter(this, mMessageList));
        } else {
            mMessageList.clear();
            mMessageList.addAll(messages);
        }
        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
    }
}

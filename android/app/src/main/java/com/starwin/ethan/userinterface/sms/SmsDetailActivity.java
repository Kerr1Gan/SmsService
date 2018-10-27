package com.starwin.ethan.userinterface.sms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.starwin.ethan.adapter.SmsListAdapter;
import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;
import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.R;

import java.util.List;

import javax.inject.Inject;

public class SmsDetailActivity extends DaggerMvpActivity<SmsComponent, SmsActivity> implements SmsDetailContract.View {

    private static final String EXTRA_PHONE_NUMBER = "phone_number_extra";

    @Inject
    List<SmsMessage> mMessageList;

    private ListView mListView;

    @Inject
    SmsDetailContract.Presenter mPresenter;

    private SwipeRefreshLayout mRefreshLayout;

    public static Intent newIntent(Context context, String phone) {
        Intent intent = new Intent(context, SmsDetailActivity.class);
        intent.putExtra(EXTRA_PHONE_NUMBER, phone);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);
        mListView = findViewById(R.id.list_view);
        mListView.setAdapter(new SmsListAdapter(this, mMessageList));
        mRefreshLayout = findViewById(R.id.swipe_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestSmsList();
            }
        });
    }

    @Override
    protected SmsComponent initDaggerComponent() {
        String phone = getIntent().getStringExtra(EXTRA_PHONE_NUMBER);
        if (phone == null) {
            finish();
            return null;
        }
        SmsComponent ret = DaggerSmsComponent.builder().build();
        ret.inject(this);
        mPresenter.bind(phone);
        return ret;
    }

    @Override
    protected SmsActivity getComponentInject() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.setRefreshing(true);
        mPresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.dropView();
    }

    @Override
    public void notifySmsMessageList(final List<SmsMessage> smsMessages) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                mMessageList.clear();
                mMessageList.addAll(smsMessages);
                ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    @Override
    public void toast(final String message) {
        mRefreshLayout.setRefreshing(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SmsDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

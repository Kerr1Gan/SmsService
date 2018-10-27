package com.starwin.ethan.userinterface.sms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.InterstitialAd;
import com.starwin.ethan.adapter.PhoneListAdapter;
import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;
import com.starwin.ethan.smsservice.R;
import com.starwin.ethan.utils.admob.AdmobCallback;
import com.starwin.ethan.utils.admob.AdmobManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class SmsActivity extends DaggerMvpActivity<SmsComponent, SmsActivity> implements SmsContract.View {

    private static final String TAG = "SmsActivity";

    @Inject
    SmsContract.Presenter mPresenter;

    private ListView mPhoneListView;

    private List<String> mPhoneList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        mPhoneListView = findViewById(R.id.list_view);
        mPhoneListView.setAdapter(new PhoneListAdapter(this, mPhoneList));
        mPhoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = SmsDetailActivity.newIntent(SmsActivity.this, mPhoneList.get(i));
                SmsActivity.this.startActivity(intent);
            }
        });

        new AdmobManager(this).loadInterstitialAd("ca-app-pub-1160759304210068/8242117294", new AdmobCallback() {
            @Override
            public void onLoaded(Object ad) {
                Log.i(TAG, "Ad loaded");
                ((InterstitialAd) ad).show();
            }

            @Override
            public void onError(int errorCode) {
                Log.i(TAG, "Ad error " + errorCode);
            }

            @Override
            public void onOpened() {
                Log.i(TAG, "Ad opened");
            }

            @Override
            public void onClosed() {
                Log.i(TAG, "Ad closed");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.dropView();
    }

    @Override
    protected SmsComponent initDaggerComponent() {
        return DaggerSmsComponent.builder().build();
    }

    @Override
    protected SmsActivity getComponentInject() {
        return this;
    }

    @Override
    public void notifyPhoneList(final String[] phone) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPhoneList.clear();
                mPhoneList.addAll(Arrays.asList(phone));
                ((BaseAdapter) mPhoneListView.getAdapter()).notifyDataSetChanged();
            }
        });
    }
}

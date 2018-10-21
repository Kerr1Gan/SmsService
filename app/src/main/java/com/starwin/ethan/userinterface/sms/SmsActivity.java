package com.starwin.ethan.userinterface.sms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;
import com.starwin.ethan.smsservice.R;

import javax.inject.Inject;

public class SmsActivity extends DaggerMvpActivity<SmsComponent, SmsActivity> implements SmsContract.View {

    @Inject
    SmsContract.Presenter mPresenter;

    private ListView mPhoneListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        mPhoneListView = findViewById(R.id.list_view);
        //mPhoneListView.setAdapter(new PhoneListAdapter(this, null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        mPresenter.requestPhoneList();
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
    public void notifyPhoneList() {

    }
}

package com.starwin.ethan.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.starwin.ethan.mvp_dagger.DaggerMvpActivity;

import javax.inject.Inject;

public class MainActivity extends DaggerMvpActivity<MainComponent, MainActivity> implements MainContract.View {

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    protected MainComponent initDaggerComponent() {
        return DaggerMainComponent.builder().mainModule(new MainModule()).build();
    }

    @Override
    protected MainActivity getComponentInject() {
        return this;
    }
}

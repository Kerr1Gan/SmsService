package com.starwin.ethan.main;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainPresenter implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void takeView(MainContract.View view) {

    }

    @Override
    public void dropView() {

    }
}

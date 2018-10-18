package com.starwin.ethan.main;

import com.starwin.ethan.mvp_dagger.dagger.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent extends BaseComponent<MainActivity> {

    MainPresenter getMainPresenter();
}

package com.starwin.ethan.main;

import android.app.Application;

import com.starwin.ethan.mvp_dagger.dagger.ApplicationModule;
import com.starwin.ethan.mvp_dagger.dagger.BaseComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, ApplicationModule.class})
public interface MainComponent extends BaseComponent<MainActivity> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        MainComponent.Builder application(Application application);

        MainComponent build();
    }
}

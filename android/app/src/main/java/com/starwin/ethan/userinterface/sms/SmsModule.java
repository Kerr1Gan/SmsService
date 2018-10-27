package com.starwin.ethan.userinterface.sms;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SmsModule {

    @Binds
    abstract SmsContract.Presenter provideSmsPresenter(SmsPresenter smsPresenter);

    static SmsPresenter provideSmsPresenteImpl() {
        return new SmsPresenter();
    }
}

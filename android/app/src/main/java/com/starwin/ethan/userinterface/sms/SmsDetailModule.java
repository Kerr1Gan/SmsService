package com.starwin.ethan.userinterface.sms;

import com.starwin.ethan.room.SmsMessage;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SmsDetailModule {

    @Binds
    abstract SmsDetailContract.Presenter provideSmsDetailPresenter(SmsDetailPresenter smsPresenter);

    @Provides
    static SmsDetailPresenter provideSmsDetailPresenterImpl() {
        return new SmsDetailPresenter();
    }

    @Provides
    static List<SmsMessage> provideSmsMessageList() {
        return new ArrayList<>();
    }
}

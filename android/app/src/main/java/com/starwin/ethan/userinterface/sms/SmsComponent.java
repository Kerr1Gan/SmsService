package com.starwin.ethan.userinterface.sms;

import com.starwin.ethan.mvp_dagger.dagger.ApplicationModule;
import com.starwin.ethan.mvp_dagger.dagger.BaseComponent;

import dagger.Component;

@Component(modules = {SmsModule.class, ApplicationModule.class, SmsDetailModule.class})
public interface SmsComponent extends BaseComponent<SmsActivity> {
    @Override
    SmsActivity inject(SmsActivity inject);

    SmsDetailActivity inject(SmsDetailActivity inject);

//    @Component.Builder
//    interface Builder {
//        SmsComponent build();
//
//        @BindsInstance
//        SmsComponent.Builder phone(String phone);
//    }
}

package com.starwin.ethan.userinterface.sms;

import com.starwin.ethan.mvp_dagger.mvp.IPresenter;
import com.starwin.ethan.mvp_dagger.mvp.IView;
import com.starwin.ethan.room.SmsMessage;

import java.util.List;

public class SmsDetailContract {
    interface View extends IView<Presenter> {

        void notifySmsMessageList(List<SmsMessage> smsMessages);

        void toast(String message);
    }

    interface Presenter extends IPresenter<View> {

        void requestSmsList();

        void bind(String phone);
    }
}

package com.starwin.ethan.userinterface.sms;

import com.starwin.ethan.mvp_dagger.mvp.IPresenter;
import com.starwin.ethan.mvp_dagger.mvp.IView;

public class SmsContract {

    interface View extends IView<SmsContract.Presenter> {

        void notifyPhoneList();
    }

    interface Presenter extends IPresenter<SmsContract.View> {

        void requestPhoneList();
    }

}

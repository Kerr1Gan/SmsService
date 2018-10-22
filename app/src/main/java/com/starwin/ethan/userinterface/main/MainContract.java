package com.starwin.ethan.userinterface.main;

import android.content.Context;
import android.support.annotation.NonNull;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.mvp_dagger.mvp.IPresenter;
import com.starwin.ethan.mvp_dagger.mvp.IView;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.entity.SmsMessage;

import java.util.List;

public interface MainContract {

    interface View extends IView<Presenter> {
        void notifySmsList(List<SmsMessage> messages);
        void showSelfPhoneDialog();
    }

    interface Presenter extends IPresenter<View> {
        void refreshSms();

        void init(SmsDatabase database, AppExecutors executors);

        void destroy();

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void go2Setting();

        void setSelfPhone();

        void saveSelfPhone(String phone, Context context);
    }
}

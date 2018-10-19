package com.starwin.ethan.main;

import com.starwin.ethan.executor.AppExecutors;
import com.starwin.ethan.mvp_dagger.mvp.IPresenter;
import com.starwin.ethan.mvp_dagger.mvp.IView;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsMessage;

import java.util.List;

public interface MainContract {

    interface View extends IView<Presenter> {
        void notifySmsList(List<SmsMessage> messages);
    }

    interface Presenter extends IPresenter<View> {
        void refreshSms();

        void init(SmsDatabase database, AppExecutors executors);

        void destroy();
    }
}

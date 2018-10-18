package com.starwin.ethan.main;

import com.starwin.ethan.mvp_dagger.mvp.IPresenter;
import com.starwin.ethan.mvp_dagger.mvp.IView;

public interface MainContract {

    interface View extends IView<Presenter> {
    }

    interface Presenter extends IPresenter<View> {
    }
}

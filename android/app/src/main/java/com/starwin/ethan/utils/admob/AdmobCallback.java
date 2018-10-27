package com.starwin.ethan.utils.admob;

public interface AdmobCallback {
    void onLoaded(Object object);

    void onError(int errorCode);

    void onOpened();

    void onClosed();
}
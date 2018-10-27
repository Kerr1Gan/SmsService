package com.starwin.ethan;

import android.app.Application;

import com.starwin.ethan.smsservice.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "3945c078d0", BuildConfig.DEBUG);
    }
}

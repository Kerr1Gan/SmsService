package com.starwin.ethan;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.starwin.ethan.smsservice.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "3945c078d0", BuildConfig.DEBUG);
        MobileAds.initialize(this, "ca-app-pub-1160759304210068~9938342349");
    }
}

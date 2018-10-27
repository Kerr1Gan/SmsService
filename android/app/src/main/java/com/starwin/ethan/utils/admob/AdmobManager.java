package com.starwin.ethan.utils.admob;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.starwin.ethan.smsservice.BuildConfig;

import java.lang.ref.WeakReference;

public class AdmobManager {

    private static final String DEVICE_ID = "20EDA9412005F81D6EBABCBCF65F02E2";

    private WeakReference<Context> mWeak;

    private AdRequest.Builder mBuilder = new AdRequest.Builder();

    public AdmobManager(Context context) {
        mWeak = new WeakReference<>(context);
        if (BuildConfig.DEBUG) {
            mBuilder.addTestDevice(DEVICE_ID);
        }
    }

    public InterstitialAd getInterstitialAd() {
        if (mWeak.get() != null) {
            return new InterstitialAd(mWeak.get());
        } else {
            return null;
        }
    }

    public InterstitialAd loadInterstitialAd(String ad, final AdmobCallback callback) {
        final InterstitialAd interstitialAd = getInterstitialAd();
        if (interstitialAd != null) {
            interstitialAd.setAdUnitId(ad);
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    callback.onClosed();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    callback.onError(i);
                }

                @Override
                public void onAdLeftApplication() {
                }

                @Override
                public void onAdOpened() {
                    callback.onOpened();
                }

                @Override
                public void onAdLoaded() {
                    callback.onLoaded(interstitialAd);
                }

                @Override
                public void onAdClicked() {
                }

                @Override
                public void onAdImpression() {
                }
            });
            interstitialAd.loadAd(mBuilder.build());
        }
        return interstitialAd;
    }

}

package com.starwin.ethan.userinterface.sms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starwin.ethan.room.entity.SmsMessage;
import com.starwin.ethan.smsservice.BuildConfig;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SmsPresenter implements SmsContract.Presenter {

    private SmsContract.View mView;

    @Inject
    public SmsPresenter() {
    }

    @Override
    public void requestPhoneList() {
        if (mView != null) {
            String url = BuildConfig.REST_URL + "/phone";
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            final Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    List<SmsMessage> smsMessages = new Gson().fromJson(response.body().string(), new TypeToken<List<SmsMessage>>() {
                    }.getType());
                    mView.notifyPhoneList();
                }
            });
        }
    }

    @Override
    public void takeView(SmsContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}

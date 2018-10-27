package com.starwin.ethan.userinterface.sms;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.starwin.ethan.smsservice.BuildConfig;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SmsPresenter implements SmsContract.Presenter {

    private SmsContract.View mView;

    @Inject
    public SmsPresenter() {
    }

    @Override
    public void requestPhoneList() {
        if (mView != null) {
            String url = BuildConfig.REST_URL + "/sms/phone";
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            final Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (mView instanceof Context) {
                        Toast.makeText((Context) mView, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String bodyStr = body.string();
                        String[] phoneArr = new Gson().fromJson(bodyStr, String[].class);
                        mView.notifyPhoneList(phoneArr);
                    }
                }
            });
        }
    }

    @Override
    public void takeView(SmsContract.View view) {
        mView = view;
        requestPhoneList();
    }

    @Override
    public void dropView() {
        mView = null;
    }
}

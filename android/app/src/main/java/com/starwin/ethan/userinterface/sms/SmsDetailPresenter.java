package com.starwin.ethan.userinterface.sms;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.starwin.ethan.room.SmsMessage;
import com.starwin.ethan.smsservice.BuildConfig;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SmsDetailPresenter implements SmsDetailContract.Presenter {

    private SmsDetailContract.View mView;

    private String mPhone;

    @Inject
    public SmsDetailPresenter() {
    }

    @Override
    public void requestSmsList() {
        String url = BuildConfig.REST_URL + "/sms?selfPhone=" + mPhone;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.toast("网络错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JsonObject jsonObject = new Gson().fromJson(response.body().string(), JsonObject.class);
                    JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                    List<SmsMessage> smsMessages = new Gson().fromJson(jsonArray, new TypeToken<List<SmsMessage>>() {
                    }.getType());
                    mView.notifySmsMessageList(smsMessages);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void bind(String phone) {
        mPhone = phone;
    }

    @Override
    public void takeView(SmsDetailContract.View view) {
        mView = view;
        requestSmsList();
    }

    @Override
    public void dropView() {
        mView = null;
    }
}

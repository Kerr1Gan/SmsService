package com.starwin.ethan.smsservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.gson.Gson;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsRepository;
import com.starwin.ethan.room.dao.SmsDao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SmsService extends IntentService {

    public static final String ACTION_SMS_SERVICE_RECEIVED_MSG = "sms_service_received_msg_action";
    private static final String TAG = "SmsService";

    private static final int RETRY_COUNT_MAX_TIMES = 10;
    private int mRetryCount = 0;

    public SmsService() {
        this("SmsService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SmsService(String name) {
        super(name);
        Log.i(TAG, "constructor SmsService invoke");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent begin");
        if (intent == null || intent.getParcelableExtra("intent") == null) {
            return;
        }
        intent = intent.getParcelableExtra("intent");
        String action = intent.getAction();
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(action) || Telephony.Sms.Intents.SMS_DELIVER_ACTION.equals(action)) {
            Log.i(TAG, "receiving msg.....");
            final int type = Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(action) ? 0 : 1;
            Bundle bundle = intent.getExtras();
            com.starwin.ethan.room.SmsMessage smsMessage = null;
            SmsDatabase smsDatabase = new SmsRepository(this).getSmsDatabase();
            final SmsDao smsDao = smsDatabase.smsDao();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null && pdus.length > 0) {

                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        byte[] pdu = (byte[]) pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                    }

                    StringBuilder content = new StringBuilder();// 得到短信内容
                    String sender = null;
                    long smsTs = System.currentTimeMillis();
                    for (SmsMessage message : messages) { // 短信会分段
                        content.append(message.getMessageBody());// 得到短信内容
                        sender = message.getOriginatingAddress();// 得到发信息的号码
                        smsTs = message.getTimestampMillis();
                    }
                    Date date = new Date(smsTs);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String sendContent = format.format(date) + ":" + sender + "--" + content;

                    smsMessage = new com.starwin.ethan.room.SmsMessage(sender, type, content.toString(), format.format(date));
                    Log.i(TAG, "receive content " + sendContent + " ");

                    smsDao.insertSms(smsMessage);
                }
                Intent broad = new Intent();
                broad.setAction(ACTION_SMS_SERVICE_RECEIVED_MSG);
                this.sendBroadcast(broad);
            }
            Log.i(TAG, "received msg.....");
            if (smsMessage != null) {
                pushMsg2Server(smsMessage, smsDao);
            }
            smsDatabase.close();
        }
        Log.i(TAG, "onHandleIntent end");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public void pushMsg2Server(com.starwin.ethan.room.SmsMessage smsMessage, SmsDao smsDao) {
        mRetryCount++;
        if (mRetryCount > RETRY_COUNT_MAX_TIMES || smsMessage.getUploaded() == 1) {
            mRetryCount = 0;
            return;
        }
        String url = BuildConfig.REST_URL + "/sms";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .method("PUT", RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(smsMessage)))
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                Log.i(TAG, "upload msg success " + new Gson().toJson(smsMessage));
                smsMessage.setUploaded(1);
                smsDao.updateSms(smsMessage);
            } else {
                pushMsg2Server(smsMessage, smsDao);
            }
        } catch (IOException e) {
            e.printStackTrace();
            pushMsg2Server(smsMessage, smsDao);
        }
    }
}

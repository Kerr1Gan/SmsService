package com.starwin.ethan.smsservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;

import com.starwin.ethan.room.SmsDao;
import com.starwin.ethan.room.SmsDatabase;
import com.starwin.ethan.room.SmsRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmsService extends IntentService {

    private static final String TAG = "SmsService";

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
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null && pdus.length > 0) {
                    SmsDatabase smsDatabase = new SmsRepository(this).getSmsDatabase();
                    final SmsDao smsDao = smsDatabase.smsDao();
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        byte[] pdu = (byte[]) pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                    }
                    for (SmsMessage message : messages) {
                        final String content = message.getMessageBody();// 得到短信内容
                        final String sender = message.getOriginatingAddress();// 得到发信息的号码

                        final Date date = new Date(message.getTimestampMillis());
                        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String sendContent = format.format(date) + ":" + sender + "--" + content;
                        Log.i(TAG, "receive content " + sendContent + " ");
                        final com.starwin.ethan.room.SmsMessage smsMessage = new com.starwin.ethan.room.SmsMessage(sender, type, content, format.format(date));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                smsDao.insertSms(smsMessage);
                            }
                        }).start();
                    }
                }
            }
            Log.i(TAG, "received msg.....");
        }
        Log.i(TAG, "onHandleIntent end");
    }
}

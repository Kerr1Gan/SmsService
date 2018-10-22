package com.starwin.ethan.room;

import android.arch.persistence.room.Room;
import android.content.Context;

public class SmsRepository {

    private SmsDatabase smsDatabase;

    public SmsRepository(Context context) {
        smsDatabase = Room.databaseBuilder(context.getApplicationContext(), SmsDatabase.class, "sms.db").build();
    }

    public SmsDatabase getSmsDatabase() {
        return smsDatabase;
    }
}

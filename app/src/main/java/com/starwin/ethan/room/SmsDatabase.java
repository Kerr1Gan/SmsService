package com.starwin.ethan.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {SmsMessage.class}, version = 1)
public abstract class SmsDatabase extends RoomDatabase {

    public abstract SmsDao smsDao();
}

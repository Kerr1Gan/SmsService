package com.starwin.ethan.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.starwin.ethan.room.dao.SmsDao;

@Database(entities = {SmsMessage.class}, version = 1)
public abstract class SmsDatabase extends RoomDatabase {

    public abstract SmsDao smsDao();
}

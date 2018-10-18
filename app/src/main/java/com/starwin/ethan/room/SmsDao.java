package com.starwin.ethan.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SmsDao {

    @Query("SELECT * FROM sms")
    List<SmsMessage> getAllSms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSms(SmsMessage sms);
}

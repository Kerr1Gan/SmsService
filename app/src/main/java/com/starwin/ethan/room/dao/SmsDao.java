package com.starwin.ethan.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.starwin.ethan.room.entity.SmsMessage;

import java.util.List;

@Dao
public interface SmsDao {

    @Query("SELECT * FROM sms ORDER BY _id DESC")
    List<SmsMessage> getAllSms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSms(SmsMessage sms);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSms(SmsMessage sms);
}

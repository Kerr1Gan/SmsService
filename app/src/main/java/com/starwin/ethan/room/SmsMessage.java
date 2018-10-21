package com.starwin.ethan.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "sms")
public class SmsMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "phone")
    private String phone;

    /**
     * 0 is receive,1 is send
     */
    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "date")
    private String date;

    /**
     * 0 is not upload to server,1 is upload to server success
     */
    @ColumnInfo(name = "uploaded")
    private int uploaded;

    public SmsMessage(String phone, int type, String content, String date) {
        this.phone = phone;
        this.type = type;
        this.content = content;
        this.date = date;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }
}

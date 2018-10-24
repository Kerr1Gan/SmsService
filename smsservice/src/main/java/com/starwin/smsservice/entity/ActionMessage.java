package com.starwin.smsservice.entity;

public class ActionMessage {

    private int id;
    private String actions;
    private String times;
    private String extra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return actions;
    }

    public void setAction(String action) {
        this.actions = action;
    }

    public String getTime() {
        return times;
    }

    public void setTime(String time) {
        this.times = time;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

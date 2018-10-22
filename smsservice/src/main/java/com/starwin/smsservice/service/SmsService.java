package com.starwin.smsservice.service;

import com.starwin.smsservice.entity.SmsMessage;

import java.util.List;

public interface SmsService {
    List<SmsMessage> getAllSmsMessages();

    List<SmsMessage> getSmsMessagesByIndex(int index, int length);

    int putSmsMessage(SmsMessage smsMessage);
}

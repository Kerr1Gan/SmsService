package com.starwin.smsservice.service.impl;

import com.starwin.smsservice.entity.SmsMessage;
import com.starwin.smsservice.mapper.SmsMapper;
import com.starwin.smsservice.service.SmsService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public class SmsServiceImpl implements SmsService {

    @Autowired
    SmsMapper mapper;

    @Override
    public List<SmsMessage> getAllSmsMessages() {
        return mapper.getAllSms();
    }

    @Override
    public List<SmsMessage> getSmsMessagesByIndex(int index, int length) {
        return mapper.getSmsByIndex(index, length);
    }

    @Override
    public int putSmsMessage(SmsMessage smsMessage) {
        return mapper.insertMessage(smsMessage);
    }
}

package com.starwin.smsservice.service.impl;

import com.starwin.smsservice.entity.ActionMessage;
import com.starwin.smsservice.mapper.ActionMapper;
import com.starwin.smsservice.service.ActionService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class ActionServiceImpl implements ActionService {

    @Autowired
    ActionMapper mapper;

    @Override
    public int putActionMessage(ActionMessage smsMessage) {
        return mapper.insertAction(smsMessage);
    }
}

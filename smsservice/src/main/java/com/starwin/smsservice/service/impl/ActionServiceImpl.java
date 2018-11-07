package com.starwin.smsservice.service.impl;

import com.starwin.smsservice.entity.ActionMessage;
import com.starwin.smsservice.entity.Value;
import com.starwin.smsservice.mapper.ActionMapper;
import com.starwin.smsservice.service.ActionService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public class ActionServiceImpl implements ActionService {

    @Autowired
    ActionMapper mapper;

    @Override
    public int putActionMessage(ActionMessage smsMessage) {
        return mapper.insertAction(smsMessage);
    }

    @Override
    public int putParams(String key, String value) {
        int ret = 0;
        try {
            ret = mapper.insertKeyValue(key, value);
        } catch (Exception e) {
            //ignore
            ret = mapper.updateKeyValue(key, value);
        }
        return ret;
    }

    @Override
    public Value getParams(String key) {
        return mapper.getValue(key);
    }

    @Override
    public List<Value> getAllParams() {
        return mapper.getAllValue();
    }
}

package com.starwin.smsservice.service;

import com.starwin.smsservice.entity.ActionMessage;
import com.starwin.smsservice.entity.Value;

import java.util.List;

public interface ActionService {

    int putActionMessage(ActionMessage smsMessage);

    int putParams(String key, String value);

    Value getParams(String key);

    List<Value> getAllParams();
}

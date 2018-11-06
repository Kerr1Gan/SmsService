package com.starwin.smsservice.jobs;

import com.starwin.smsservice.entity.Value;
import com.starwin.smsservice.mapper.ActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleJob {

    @Autowired
    ActionMapper actionMapper;

    @Scheduled(fixedRate = 10 * 1000)
    public void fixedRateJob() {
        Value value = actionMapper.getValue("value1");
        Value value2 = actionMapper.getValue("value2");
        Value value3 = actionMapper.getValue("value3");

        if (value == null) {
            actionMapper.insertKeyValue("value1", "1");
            actionMapper.insertKeyValue("value2", "1");
            actionMapper.insertKeyValue("value3", "1");
            return;
        }

        int random = ((int) (Math.random() * 1000)) % 3;
        if (random == 0) {
            value = value;
        } else if (random == 1) {
            value = value2;
        } else {
            value = value3;
        }
        try {
            value.setValue(String.valueOf(Integer.parseInt(value.getValue()) + 1));
            actionMapper.updateKeyValue(value.getKey(), value.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

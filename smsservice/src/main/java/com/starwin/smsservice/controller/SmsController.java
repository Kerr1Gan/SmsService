package com.starwin.smsservice.controller;

import com.starwin.smsservice.entity.SmsMessage;
import com.starwin.smsservice.entity.response.CommonResult;
import com.starwin.smsservice.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("")
@RestController
public class SmsController {

    @Autowired
    SmsService smsService;

    @RequestMapping(value = "/sms", method = RequestMethod.GET)
    public CommonResult getSms(@RequestParam(required = false, defaultValue = "0") Integer index,
                               @RequestParam(required = false, defaultValue = "50") Integer length,
                               @RequestParam(required = false, defaultValue = "") String selfPhone) {
        List<SmsMessage> smsMessages = smsService.getSmsMessagesByIndex(index, length, selfPhone);
        return new CommonResult<>("0", "success", smsMessages);
    }

    @RequestMapping(value = "/sms", method = RequestMethod.PUT)
    public CommonResult putSms(@RequestBody SmsMessage smsMessage) {
        int ret = smsService.putSmsMessage(smsMessage);
        return new CommonResult<>("0", "success", ret);
    }

    @RequestMapping(value = "/sms/phone", method = RequestMethod.GET)
    public List<String> getPhoneList(@RequestParam(required = false, defaultValue = "0") Integer index,
                                     @RequestParam(required = false, defaultValue = "50") Integer length) {
        return smsService.getPhoneList(index, length);
    }
}

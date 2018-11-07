package com.starwin.smsservice.controller;

import com.starwin.smsservice.entity.ActionMessage;
import com.starwin.smsservice.entity.Value;
import com.starwin.smsservice.entity.response.CommonResult;
import com.starwin.smsservice.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActionController {

    @Autowired
    ActionService actionService;

    @RequestMapping(value = "/action", method = {RequestMethod.PUT, RequestMethod.POST})
    public CommonResult putAction(@RequestBody ActionMessage actionMessage) {
        int ret = actionService.putActionMessage(actionMessage);
        return new CommonResult<>("0", "success", ret);
    }

    @RequestMapping(value = "/value", method = {RequestMethod.PUT, RequestMethod.POST})
    public CommonResult putParam(@RequestParam String key, @RequestParam String value) {
        int ret = actionService.putParams(key, value);
        return new CommonResult<>("0", "success", ret);
    }

    @RequestMapping(value = "/value", method = {RequestMethod.GET})
    public CommonResult getParam(@RequestParam String key) {
        Value ret = actionService.getParams(key);
        return new CommonResult<>("0", "success", ret);
    }

    @RequestMapping(value = "/value/all", method = {RequestMethod.GET})
    public CommonResult getAllParam() {
        List<Value> ret = actionService.getAllParams();
        return new CommonResult<>("0", "success", ret);
    }
}

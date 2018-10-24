package com.starwin.smsservice.controller;

import com.starwin.smsservice.entity.ActionMessage;
import com.starwin.smsservice.entity.response.CommonResult;
import com.starwin.smsservice.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionController {

    @Autowired
    ActionService actionService;

    @RequestMapping(value = "/action", method = RequestMethod.PUT)
    public CommonResult putAction(@RequestBody ActionMessage actionMessage) {
        int ret = actionService.putActionMessage(actionMessage);
        return new CommonResult<>("0", "success", ret);
    }
}

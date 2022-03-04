package com.strawberry.httpclientserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping(value = "/params", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String params(String name, String password) {
        System.out.println("name - " + name + ", password - " + password);
        return "{\"msg\":\"登录成功\", \"user\":{\"name\":\"" + name + "\",\"password\":" + password + "\"}}";
    }

    @RequestMapping(value = "/test", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String test() {
        return "{\"msg\":\"处理返回\"}";
    }
}

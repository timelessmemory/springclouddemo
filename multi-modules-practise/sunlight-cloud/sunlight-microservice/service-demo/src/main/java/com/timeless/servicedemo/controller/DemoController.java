package com.timeless.servicedemo.controller;

import com.timeless.sunlightcommoncore.bean.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${name}")
    private String name;

    @RequestMapping("/demo")
    public Result demo() {
        return new Result<String>().returnSuccessWithData(name);
    }
}

package com.mario.eureka_client_consumer_hystrix.controller;

import com.mario.eureka_client_consumer_hystrix.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/loginByFeign")
    public String loginByFeign() {
        return loginService.login();
    }

}

package com.mario.eureka_client_consumer.controller;

import com.mario.eureka_client_consumer.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login() {
//        return restTemplate.getForObject("http://localhost:8000/service/login", String.class);

        //通过服务名称（spring.application.name）调用
        return restTemplate.getForObject("http://eureka-client-provider/service/login", String.class);
    }

    @GetMapping("/loginByFeign")
    public String loginByFeign() {
        return loginService.login();
    }
}

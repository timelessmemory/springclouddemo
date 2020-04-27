package com.timeless.eureka_client_consumer.controller;

import com.timeless.eureka_client_consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/loginRest")
    public String loginRest() {
        String id = "1";
//        return restTemplate.getForObject("http://127.0.0.1:7000/user/getUserInfo/" + id, String.class);
//        restTemplate.postForObject()
        return restTemplate.getForObject("http://web-eureka-client-provider/user/getUserInfo/" + id, String.class);
    }

    @GetMapping("/login/{id}")
    public String login(@PathVariable("id") String id) {
        return userService.getUserInfo(id);
    }
}

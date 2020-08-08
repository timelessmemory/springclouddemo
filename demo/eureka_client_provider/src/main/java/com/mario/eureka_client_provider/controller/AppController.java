package com.mario.eureka_client_provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class AppController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

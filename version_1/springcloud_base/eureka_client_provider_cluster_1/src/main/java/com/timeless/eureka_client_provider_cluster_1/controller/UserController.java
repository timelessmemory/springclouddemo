package com.timeless.eureka_client_provider_cluster_1.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUserInfo/{id}")
    public String getUserInfo(@PathVariable("id") String id) {
        return id;
    }

}

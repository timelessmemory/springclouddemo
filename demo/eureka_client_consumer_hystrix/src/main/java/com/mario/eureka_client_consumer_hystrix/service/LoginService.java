package com.mario.eureka_client_consumer_hystrix.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "eureka-client-provider", fallbackFactory = LoginServiceFallbackFactory.class)
public interface LoginService {

    @GetMapping("/service/login")
    public String login();
}

package com.timeless.eureka_client_consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("web-eureka-client-provider")
public interface UserService {

    @RequestMapping(value = "/user/getUserInfo/{id}")
    public String getUserInfo(@PathVariable("id") String id);
}

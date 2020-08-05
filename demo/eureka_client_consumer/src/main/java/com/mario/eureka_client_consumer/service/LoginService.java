package com.mario.eureka_client_consumer.service;

import com.mario.eureka_client_consumer.configuration.FeignConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "eureka-client-provider", configuration = FeignConfiguration.class, fallback = LoginServiceFallback.class)//指定要调用的提供者应用名称，也就是服务名称
public interface LoginService {

    @GetMapping("/service/login")
    public String login();
}

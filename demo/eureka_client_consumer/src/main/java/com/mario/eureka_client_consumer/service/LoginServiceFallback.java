package com.mario.eureka_client_consumer.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceFallback implements LoginService {

    /**
     * 还有一种方式 实现implements FallbackFactory<UserFeignClient>
     *     可以知道回退原因 @Override
     *     public UserFeignClient create(Throwable throwable) {}
     *     详见eureka-client-consumer-hystrix项目
     * @return
     */
    @Override
    public String login() {
        return "error";
    }
}

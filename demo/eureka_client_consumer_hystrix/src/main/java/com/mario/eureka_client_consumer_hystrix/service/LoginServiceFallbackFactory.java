package com.mario.eureka_client_consumer_hystrix.service;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceFallbackFactory implements FallbackFactory<LoginService> {

    private Logger logger = LoggerFactory.getLogger(LoginServiceFallbackFactory.class);

    @Override
    public LoginService create(final Throwable cause) {

        logger.error("LoginService回退：", cause);

        return new LoginService() {
            @Override
            public String login() {
                return "error";
            }
        };
    }
}

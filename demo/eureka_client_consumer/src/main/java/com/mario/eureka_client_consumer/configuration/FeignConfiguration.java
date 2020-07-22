package com.mario.eureka_client_consumer.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    /**
     * 日志级别
     * NONE：不输出日志。
     * BASIC：只输出请求方法的 URL 和响应的状态码以及接口执行的时间。
     * HEADERS：将 BASIC 信息和请求头信息输出。
     * FULL：输出完整的请求信息。
     *
     * feign显示日志的步骤
     * 1.定义该配置类
     * 2.@FeignClient(value = "xx", configuration = FeignConfiguration.class) 中指定使用的配置类
     * 3.application.properties加上logging.level.该类的全限定名=DEBUG
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

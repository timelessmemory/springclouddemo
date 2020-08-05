package com.mario.springcloud_zuul.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public IpFilter ipFilter() {
        return new IpFilter();
    }

}

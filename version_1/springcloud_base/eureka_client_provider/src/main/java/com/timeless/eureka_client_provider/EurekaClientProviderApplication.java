package com.timeless.eureka_client_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientProviderApplication.class, args);
    }

}

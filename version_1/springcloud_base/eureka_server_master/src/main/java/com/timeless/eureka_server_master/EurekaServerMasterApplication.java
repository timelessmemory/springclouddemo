package com.timeless.eureka_server_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerMasterApplication.class, args);
    }

}

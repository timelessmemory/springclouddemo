package com.timeless.sunlightmonitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class SunlightMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunlightMonitorApplication.class, args);
    }

}

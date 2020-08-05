package com.timeless.eureka_client_provider_cluster_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientProviderCluster2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientProviderCluster2Application.class, args);
    }

}
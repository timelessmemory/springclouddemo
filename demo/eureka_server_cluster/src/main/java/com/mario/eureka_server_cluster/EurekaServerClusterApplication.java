package com.mario.eureka_server_cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//注册中心集群 开启多实例互相注册
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerClusterApplication.class, args);
    }

}

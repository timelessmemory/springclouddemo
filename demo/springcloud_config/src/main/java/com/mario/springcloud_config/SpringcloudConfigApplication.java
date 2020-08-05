package com.mario.springcloud_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;


/**
 * springcloud config 分为服务端 客户端
 * 服务端统一管理配置并注册到eureka 不需要把项目所有配置都放到服务端 把经常变动的放到服务端统一管理
 * 客户端可以读取服务器配置
 * 本项目是服务端 配置中心 本地保存配置 也可以存到git
 * 客户端使用见eureka_client_consumer项目
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class SpringcloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConfigApplication.class, args);
    }

}

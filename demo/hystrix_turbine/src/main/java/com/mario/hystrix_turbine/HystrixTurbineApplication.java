package com.mario.hystrix_turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 启动项目访问http://localhost:20000/turbine.stream
 * 进入dashboard页面http://localhost:10000/hystrix填写该地址即可可视化
 * 注意点 1.被监控消费者需要向注册中心注册自己eureka.client.register-with-eureka=true
 * 2、turbine.cluster-name-expression=new String("default") 这个default不能改（这是自己测试发现的）
 */
@EnableTurbine
@SpringBootApplication
public class HystrixTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbineApplication.class, args);
    }

}

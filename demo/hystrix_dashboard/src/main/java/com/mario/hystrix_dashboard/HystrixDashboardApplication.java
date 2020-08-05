package com.mario.hystrix_dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * dashboard一般独立开一个项目
 * dashboard项目 -> 消费者 -> 多个提供者
 * 监控请求成功、失败、熔断情况
 * EnableHystrixDashboard接入 见word文档笔记
 * 导包
 * 访问 http://localhost:10000/hystrix填写要监控的服务
 * http://localhost:9000/actuator/hystrix.stream
 *
 * 根据版本 被监控服务需要添加如下代码
 * @Bean
 *     public ServletRegistrationBean getServlet(){
 *         HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
 *         ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
 *         registrationBean.setLoadOnStartup(1);
 *         registrationBean.addUrlMappings("/actuator/hystrix.stream");
 *         registrationBean.setName("HystrixMetricsStreamServlet");
 *         return registrationBean;
 *     }
 */
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }

}

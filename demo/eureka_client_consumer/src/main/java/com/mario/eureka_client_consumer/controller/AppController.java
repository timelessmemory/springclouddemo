package com.mario.eureka_client_consumer.controller;

import com.mario.eureka_client_consumer.service.LoginService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginService loginService;

    @Value("${test.springcloudconfig.value}")
    private String test_springcloudconfig_value;

    @GetMapping("/login")
    public String login() {
//        return restTemplate.getForObject("http://localhost:8000/service/login", String.class);

        //通过服务名称（spring.application.name）调用
        return restTemplate.getForObject("http://eureka-client-provider/service/login", String.class);
    }

    //单个方法降级
    //与feign结合类级别降级 两者共存时走方法级别降级
    @HystrixCommand(fallbackMethod ="loginByFeignFallback", commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value = "THREAD")//指定隔离策略
            //THREAD：线程隔离，在单独的线程上执行，并发请求受线程池大小的控制。
            //SEMAPHORE：信号量隔离，在调用线程上执行，并发请求受信号量计数器的限制
    })
    @GetMapping("/loginByFeign")
    public String loginByFeign() {
        return loginService.login();
    }

    public String loginByFeignFallback() {
        return "loginByFeignFallback";
    }

    /**
     * 测试 springcloud config获取配置中心的值
     */
    @RequestMapping("/test")
    public void test() {
        System.out.println(test_springcloudconfig_value);
    }
}

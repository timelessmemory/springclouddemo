package com.timeless.eureka_client_consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaClientConsumerApplication.class)
public class EurekaClientConsumerApplicationTests {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void test() {
        String serviceId = "web-eureka-client-provider";

        for (int i = 0; i < 100; i++) {
            ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
            System.out.println("第" + (i + 1) + "次：" + serviceInstance.getHost() + ": " + serviceInstance.getPort());
        }
    }

}

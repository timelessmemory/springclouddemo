package com.timeless.eureka_client_consumer.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CustomRibbonRule extends AbstractLoadBalancerRule {

    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    public Server choose(ILoadBalancer loadBalancer, Object key) {

        List<Server> servers = loadBalancer.getAllServers();

        for (Server server : servers) {
            log.info(server.getHostPort());
        }

        return servers.get(0);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}

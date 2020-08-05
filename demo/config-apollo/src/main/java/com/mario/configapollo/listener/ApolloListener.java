package com.mario.configapollo.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApolloListener {

    @ApolloConfig
    private Config config;

    /**
     * 监听变化
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    private void configChangeListter(ConfigChangeEvent changeEvent) {
        System.out.println(changeEvent.isChanged("server.port"));
    }

}

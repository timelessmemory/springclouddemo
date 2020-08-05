package com.mario.configapollo.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Value( "${server.port}" )
    private String port;

    @GetMapping("/config")
    public String config() {

        logger.debug( "debug log..." );

//        Config config = ConfigService.getAppConfig();
//        String defaultValue = "50";
//        String port = config.getProperty("server.port", defaultValue);
//        System.out.println("port=" + port);

        return " port:" + port;
    }
}

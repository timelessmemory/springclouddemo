package com.mario.configapollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这边系统demo是直接通过apollo获取配置中心的值
 *
 * quickstart
 * 下载apollo-quick-start-1.6.1 解压
 * 数据库建两个表
 * 修改demo.sh 连接数据库信息
 * ./demo.sh start
 *
 * 具体通过源码搭建环境见文档 Apollo实践
 */
@SpringBootApplication
@EnableApolloConfig
public class ConfigApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApolloApplication.class, args);
    }

}

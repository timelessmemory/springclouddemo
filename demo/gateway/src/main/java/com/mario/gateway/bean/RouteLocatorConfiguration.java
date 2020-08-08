package com.mario.gateway.bean;

import com.mario.gateway.filter.ExecuteTimeFilter;
import com.mario.gateway.filter.TokenFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class RouteLocatorConfiguration {

    /**
     * 代码方式配置路由 可以配置自定义过滤器 这种配置，过滤器只针对某种路由起作用
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {

        /**
         * 访问http://localhost:888/zidingyi/qianzui/app/loginByFeign
         * 匹配到zidingyi/qianzui
         * 由于stripPrefix(2) 去除两层以后就是http://localhost:888/app/loginByFeign
         * 转到服务eureka-client-consumer-hystrix的app/loginByFeign
         *
         * 原来是这么访问的 http://localhost:888/EUREKA-CLIENT-CONSUMER-HYSTRIX/app/loginByFeign
         */
        return builder.routes()
                //匹配/zidingyi/qianzui/**这样的路径
                .route(r -> r.path("/zidingyi/qianzui/**")
                                //2表示去除路径前两层前缀
                                .filters(f -> f.stripPrefix(2)
                                                //自定义过滤器
                                                .filter(new ExecuteTimeFilter())
                                                //自定义响应头
                                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                                //转到服务名对应的服务lb://是固定开头
                                .uri("lb://eureka-client-consumer-hystrix")
                                .order(0)//优先级 越小越优先
                                .id("eureka-client-consumer-hystrix-service")//唯一标识
                )
                .build();
    }

    /**
     * 配置全局过滤器
     * @return
     */
//    @Bean
//    public TokenFilter tokenFilter(){
//        return new TokenFilter();
//    }

    /**
     * 设置根据请求IP地址来限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        //这种写法相当于返回一个实现了KeyResolver接口的类实例 exchange是KeyResolver接口中方法的入参
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}

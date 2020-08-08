package com.mario.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 这种写法的过滤器只针对某种路由起作用 局部过滤器
 */
public class ExecuteTimeFilter implements GatewayFilter, Ordered {

    private static final Log log = LogFactory.getLog(ExecuteTimeFilter.class);
    private static final String EXECUTE_TIME_BEGIN = "ExecuteTimeBeginKey";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //pre：之前执行
        exchange.getAttributes().put(EXECUTE_TIME_BEGIN, System.currentTimeMillis());

        return chain.filter(exchange).then(//post：之后执行
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(EXECUTE_TIME_BEGIN);

                    if (startTime != null) {
                        log.info(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public ShortcutType shortcutType() {
        return null;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return null;
    }

    @Override
    public String shortcutFieldPrefix() {
        return null;
    }
}

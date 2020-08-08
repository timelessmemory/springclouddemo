package com.mario.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义过滤器工厂 过滤器工厂需要继承 AbstractGatewayFilterFactory 类，重写 apply 方法的逻辑。命名需要以 GatewayFilterFactory 结尾，比如 AccessAuthGatewayFilterFactory，那么在使用的时候 AccessAuth就是这个过滤器工厂的名称
 * StripPrefix、AddResponseHeader 这两个实际上是两个过滤器工厂（GatewayFilterFactory），用这种配置的方式更灵活方便
 * 以下改写ExecuteTimeFilter 使用过滤器工厂方式
 */
@Component
public class ExecuteGatewayFilterFactory extends AbstractGatewayFilterFactory<ExecuteGatewayFilterFactory.Config> {

    private static final Log log = LogFactory.getLog(ExecuteGatewayFilterFactory.class);
    private static final String EXECUTE_TIME_BEGIN = "ExecuteTimeBeginKey";
    private static final String KEY = "isOpen";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public ExecuteGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //这种写法就是相当于写了一个实现GatewayFilter接口的匿名内部类
        return (exchange, chain) -> {
            exchange.getAttributes().put(EXECUTE_TIME_BEGIN, System.currentTimeMillis());

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(EXECUTE_TIME_BEGIN);

                        if (startTime != null) {
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                    .append(": ")
                                    .append(System.currentTimeMillis() - startTime)
                                    .append("ms");
                            if (config.isOpen()) {
                                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                            }
                            log.info(sb.toString());
                        }
                    })
            );
        };
    }

    public static class Config {

        private boolean isOpen;

        public boolean isOpen() {
            return isOpen;
        }

        public void setIsOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }
    }
}

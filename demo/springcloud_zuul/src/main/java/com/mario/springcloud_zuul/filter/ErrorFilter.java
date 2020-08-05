package com.mario.springcloud_zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zuul中为我们提供了一个异常处理的过滤器，当过滤器在执行过程中发生异常，若没有被捕获到，就会进入 error过滤器中
 */
public class ErrorFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        log.error("Filter Erroe : {}", throwable.getCause().getMessage());
        return null;
    }
}

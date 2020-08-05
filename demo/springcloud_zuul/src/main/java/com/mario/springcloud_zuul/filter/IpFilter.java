package com.mario.springcloud_zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ocsp.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 *
 * javax.servlet.Filter 只有一种类型，可以通过配置 urlPatterns 来拦截对应的请求
 * 而Zuul中的过滤器总共有 4 种类型，且每种类型都有对应的使用场景。
 * 1）pre
 * 可以在请求被路由之前调用。适用于身份认证的场景，认证通过后再继续执行下面的流程。
 * 2）route
 * 在路由请求时被调用。适用于灰度发布场景，在将要路由的时候可以做一些自定义的逻辑。
 * 3）post
 * 在 route 和 error 过滤器之后被调用。这种过滤器将请求路由到达具体的服务之后执行。适用于需要添加响应头，记录响应日志等应用场景。
 * 4）error
 * 处理请求时发生错误时被调用。在执行过程中发送错误时会进入 error 过滤器，可以用来统一记录错误信息。
 *
 *
 * 过滤器定义完成之后我们需要配置过滤器才能生效
 */
public class IpFilter extends ZuulFilter {

    // IP黑名单列表
    private List<String> blackIpList = Arrays.asList("127.0.0.1");

    @Override
    public String filterType() {//指定过滤器类型 可选值有 pre、route、post、error
        return "pre";
    }

    @Override
    public int filterOrder() {//过滤器的执行顺序，数值越小，优先级越高
        return 1;
    }

    @Override
    public boolean shouldFilter() {//是否执行该过滤器，true 为执行，false 为不执行，这个也可以利用配置中心来实现，达到动态的开启和关闭过滤器 zuul.IpFilter(过滤器的类名).pre(过滤器类型).disable=true
        RequestContext ctx = RequestContext.getCurrentContext();
        Object success = ctx.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    /**
     * 过滤器中传递数据
     * 第一个过滤器需要告诉第二个过滤器一些信息，这个时候就涉及在过滤器中怎么去传递数据给后面的过滤器。
     * RequestContext ctx = RequestContext.getCurrentContext();
     * ctx.set("msg", "111");
     * ctx.get("msg");
     */

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String ip = null;

        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }

        if (StringUtils.isNotBlank(ip) && blackIpList.contains(ip)) {
            ctx.setSendZuulResponse(false);// 不需要将当前请求转发到后端的服务
            ctx.set("sendForwardFilter.ran", true);//用来拦截本地转发请求的，当我们配置了forward：/local的路由，ctx.setSendZuulResponse(false) 对 forward 是不起作用的，需要设置 ctx.set("sendForwardFilter.ran"，true) 才行
            ctx.setResponseBody("ip禁用");
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;//zuul中这样返回 后面过滤器还会执行 方法就是添加ctx.set("isSuccess", false);传递参数给下一个过滤器 在下一个过滤器的shouldFilter中取出进行判断
        }

//        throw new RuntimeException();//测试异常过滤器ErrorFilter

        return null;
    }
}

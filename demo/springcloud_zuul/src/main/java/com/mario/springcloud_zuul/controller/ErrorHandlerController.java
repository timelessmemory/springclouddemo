package com.mario.springcloud_zuul.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * 异常过滤器出现异常时返回的是/error页面 不是json
 * Spring Boot中统一进行异常处理的办法，把页面的错误转换成了统一的Json 格式数据返回给调用方， 在 Zuul中无效
 * 因为@ControllerAdvice注解主要用来针对 Controller中的方法做处理，作用于 @RequestMapping标注的方法上，只对我们定义的接口异常有效
 * zuul中解决方案如下
 */
@RestController
public class ErrorHandlerController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Map error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request);

        String message = (String) errorAttributes.get("message");
        String trace = (String) errorAttributes.get("trace");

        if (StringUtils.isNotBlank(trace)) {
            message += String.format("and trace %s", trace);
            System.out.println(message);
        }

        return errorAttributes;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
    }
}

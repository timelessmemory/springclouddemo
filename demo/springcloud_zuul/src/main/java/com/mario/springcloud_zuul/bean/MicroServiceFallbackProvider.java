package com.mario.springcloud_zuul.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * zuul降级
 * Zuul默认整合了 Hystrix，当后端服务异常时可以为Zuul添加回退功能，返回默认的数据给客户端
 *
 */
@Component
public class MicroServiceFallbackProvider implements FallbackProvider {

    private Logger log = LoggerFactory.getLogger(MicroServiceFallbackProvider.class);

    @Override
    public String getRoute() {
        //对所有服务进行回退操作，如果只想对某个服务进行回退，那么就返回需要回退的服务名称，这个名称一定要是注册到 Eureka 中的名称
        return "*";
    }

    /**
     * 通过 ClientHttpResponse构造回退的内容。
     * 通过 getStatusCode返回响应的状态码。
     * 通过 getStatusText返回响应状态码对应的文本。
     * 通过 getBody返回回退的内容。
     * 通过 getHeaders返回响应的请求头信息。
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {

                if (cause != null) {
                    log.error("", cause.getCause());
                }

                String result = "{'msg':'error'}";
                return new ByteArrayInputStream(result.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }
}

package com.mario.swaggerdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"用户接口"})
@RestController
public class SwaggerController {

    @ApiOperation(value = "测试接口", nickname = "测试接口")
    @RequestMapping("/test")
    public String test(@ApiParam(value = "姓名") String name) {
        System.out.println(name);
        return name;
    }
}

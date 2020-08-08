package com.timeless.sunlightgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

    @GetMapping("/fallback")
    public String fallback() {
        return "fallback";
    }
}

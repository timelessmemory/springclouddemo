package com.mario.springcloud_zuul.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalController {

    @RequestMapping("/local/{id}")
    public String local(@PathVariable String id) {
        return id;
    }
}

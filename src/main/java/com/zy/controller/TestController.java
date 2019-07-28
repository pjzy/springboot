package com.zy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZhaoYong on 20190330
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/t1")
    public String hello(){
        return "hello world";
    }
    @RequestMapping("/t2")
    public String hello2(){
        return "hello world";
    }
}

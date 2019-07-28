package com.zy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ZhaoYong on 20190330
 */
@Api("权限测试")
@RestController
@RequestMapping("/auth")
public class AuthTestController {

    @ApiOperation("hello方法")
    @RequestMapping(method ={RequestMethod.POST, RequestMethod.GET},value= "/a1",
            consumes={"application/json"}, produces={"application/json"}, params={"name=mike","pwd=123456"},headers={"a=1"})
    public String hello(HttpServletRequest request, HttpServletResponse response){
        return "hello world";
    }

    @ApiOperation("hello2方法")
    @RequestMapping("/a2")
    public String hello2(){
        return "hello world";
    }
}

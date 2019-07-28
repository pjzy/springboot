package com.zy.controller;

import com.zy.entities.Order;
import com.zy.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zy
 * @Date: 2019/7/27 15:05
 * @Description:
 */
@Api("主数据库 订单查询")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Integer add(@RequestBody Order order) {
        return service.add(order);
    }

    @RequestMapping(value="/get/{id}",method = RequestMethod.GET)
    public Order get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Order> list(){
        return service.list();
    }
}

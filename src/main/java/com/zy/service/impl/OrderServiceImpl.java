package com.zy.service.impl;

import com.zy.entities.Order;
import com.zy.entities.OrderExample;
import com.zy.mapper.owner.OrderMapper;
import com.zy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public Integer add(Order order) {
        return orderMapper.insert(order);
    }
    public Order get(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order==null) {
            throw new RuntimeException("Order:"+id+" 不存在");
        }
        return order;
    }
    public List<Order> list(){
        return orderMapper.selectByExample(new OrderExample());
    }
}

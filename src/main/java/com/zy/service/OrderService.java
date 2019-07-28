package com.zy.service;

import com.zy.entities.Order;

import java.util.List;

public interface OrderService {
    public Integer add(Order order);
    public Order get(Integer id);
    public List<Order> list();
}

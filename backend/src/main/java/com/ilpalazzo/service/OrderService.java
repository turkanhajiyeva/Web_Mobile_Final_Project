package com.ilpalazzo.service;

import com.ilpalazzo.model.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    void deleteOrder(Long id);
}

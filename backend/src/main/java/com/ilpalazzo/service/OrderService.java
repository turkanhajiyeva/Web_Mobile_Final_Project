package com.ilpalazzo.service;

import java.util.List;

import com.ilpalazzo.model.entity.Order;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    void deleteOrder(Long id);
    Order updateOrderStatus(Long orderId, String newStatus);
}

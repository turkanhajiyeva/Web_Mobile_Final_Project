package com.ilpalazzo.service.impl;

import com.ilpalazzo.errors.OrderNotFoundException;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.repository.OrderRepository;
import com.ilpalazzo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));

        String currentStatus = order.getStatus();
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        if ("ready".equalsIgnoreCase(newStatus) && !"ready".equalsIgnoreCase(currentStatus)) {
            log.info("Notify waiter: Order #{} is ready for delivery.", orderId);
        }

        if ("delivered".equalsIgnoreCase(newStatus) && !"delivered".equalsIgnoreCase(currentStatus)) {
            log.info("Order #{} has been marked as delivered by a waiter.", orderId);
        }

        return updatedOrder;
    }
}
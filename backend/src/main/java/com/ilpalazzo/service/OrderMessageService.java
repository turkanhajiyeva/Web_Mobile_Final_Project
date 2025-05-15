package com.restaurant.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.model.Order;
@Service
public class OrderMessageService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order) {
        rabbitTemplate.convertAndSend("restaurant-exchange", "order.new", order);
    }
}
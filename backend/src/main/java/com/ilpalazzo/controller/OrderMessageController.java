package com.ilpalazzo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ilpalazzo.model.entity.Order;

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
package com.ilpalazzo.rabbit;

import com.ilpalazzo.model.entity.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderMessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String EXCHANGE_NAME = "order-exchange";
    private static final String ROUTING_KEY = "order.routing.key";

    public void sendOrder(Order order) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String orderJson = objectMapper.writeValueAsString(order); 
            amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, orderJson); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

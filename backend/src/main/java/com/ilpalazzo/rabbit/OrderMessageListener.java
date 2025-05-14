package com.ilpalazzo.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void listenOrderMessages(String message) {
        System.out.println("Received order message: " + message);
        // You can process the order message here
    }
}

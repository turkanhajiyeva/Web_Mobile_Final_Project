package com.ilpalazzo.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.ilpalazzo.model.dto.OrderNotificationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;


    @Value("${sample.rabbitmq.exchange}")
    private String exchange;

    @Value("${sample.rabbitmq.routingkey}")
    private String routingKey;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(OrderNotificationDto notification) {
        if (notification == null ||
        notification.getOrderId() == null ||
        notification.getUserId() == null ||
        notification.getStatus() == null) 
        {
        System.err.println("❌ Notification is missing required fields. Not sending to RabbitMQ.");
        return;
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, notification);
    }
}

package com.ilpalazzo.rabbit;

import com.ilpalazzo.model.dto.OrderNotificationDto;
import com.ilpalazzo.notifsender.NotificationWebSocketSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    private final NotificationWebSocketSender webSocketSender;

    public OrderMessageListener(NotificationWebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void receiveNotification(OrderNotificationDto notification) {
        System.out.println("Received: " + notification);
        webSocketSender.sendToUser(notification.getUserId(), notification);
    }
}

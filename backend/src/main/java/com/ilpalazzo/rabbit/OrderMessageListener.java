package com.ilpalazzo.rabbit;

import com.ilpalazzo.model.dto.OrderNotificationDto;
import com.ilpalazzo.notifsender.NotificationWebSocketSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);

    private final NotificationWebSocketSender webSocketSender;

    public OrderMessageListener(NotificationWebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void receiveNotification(OrderNotificationDto notification) {
        if (notification == null) {
            logger.warn("Received null notification message");
            return;
        }

        logger.info("Received order notification: {}", notification);

        // Send notification to the WebSocket user
        webSocketSender.sendToUser(notification.getUserId(), notification);
    }
}

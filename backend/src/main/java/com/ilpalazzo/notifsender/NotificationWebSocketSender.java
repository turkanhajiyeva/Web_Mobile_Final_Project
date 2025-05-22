package com.ilpalazzo.notifsender;

import com.ilpalazzo.model.dto.OrderNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationWebSocketSender {

    private static final Logger logger = LoggerFactory.getLogger(NotificationWebSocketSender.class);

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationWebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendToUser(String userId, OrderNotificationDto notification) {
        logger.info("Sending notification to user [{}]: {}", userId, notification);
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notification);
    }
}

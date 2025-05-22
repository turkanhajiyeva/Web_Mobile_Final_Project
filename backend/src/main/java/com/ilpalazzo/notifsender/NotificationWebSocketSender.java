package com.ilpalazzo.notifsender;

import com.ilpalazzo.model.dto.OrderNotificationDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationWebSocketSender {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationWebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendToUser(String userId, OrderNotificationDto notification) {
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notification);
    }
}

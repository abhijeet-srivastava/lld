package com.twisha.notificationservice.service;

import com.twisha.notificationservice.model.NotificationRequest;
import com.twisha.notificationservice.model.NotificationType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    private final RabbitTemplate template;

    public NotificationManager(RabbitTemplate template) {
        this.template = template;
    }

    public void enqueueNotification(NotificationRequest request) {
        if(!isValidType(request.getType())) {
            throw new IllegalArgumentException("Invalid notification type");
        }
        this.template.convertAndSend("Notification", request);
    }

    private boolean isValidType(NotificationType type) {
        return !(
                type == NotificationType.EMAIL || type == NotificationType.SMS
                );
    }
}

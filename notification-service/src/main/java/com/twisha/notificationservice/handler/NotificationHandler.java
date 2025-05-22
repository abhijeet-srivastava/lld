package com.twisha.notificationservice.handler;

import com.twisha.notificationservice.model.NotificationRequest;
import org.springframework.stereotype.Component;


public interface NotificationHandler {

    void handleNotification(NotificationRequest request);
}

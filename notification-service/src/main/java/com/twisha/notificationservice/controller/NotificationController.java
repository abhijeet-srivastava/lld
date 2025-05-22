package com.twisha.notificationservice.controller;


import com.twisha.notificationservice.model.NotificationRequest;
import com.twisha.notificationservice.service.NotificationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/v1/notifications")
public class NotificationController {

    private final NotificationManager manager;

    public NotificationController(NotificationManager manager) {
        this.manager = manager;
    }

    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            manager.enqueueNotification(request);
            return ResponseEntity.ok("SUCCESS");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

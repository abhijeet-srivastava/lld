package com.twisha.notificationservice.handler;

import com.twisha.notificationservice.model.NotificationRequest;
import com.twisha.notificationservice.model.NotificationType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailNotificationHandler implements  NotificationHandler{

    private JavaMailSender mailSender;

    public EmailNotificationHandler(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @RabbitListener(queues = "notifications")
    public void handleNotification(NotificationRequest request) {
        if(request.getType() == NotificationType.EMAIL) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper msgHelper = new MimeMessageHelper(message, true);
                msgHelper.setFrom("noreply@example.com");
                msgHelper.setTo(request.getRecipient());
                msgHelper.setSubject(request.getContent().getSubject());
                msgHelper.setText(request.getContent().getBody());

                mailSender.send(message);

            } catch (MessagingException e) {
                System.err.printf("Error sending email notification");
                //throw new RuntimeException(e);
                // TODO: Implement retry logic or dead-letter queue
            }
        }
    }
}

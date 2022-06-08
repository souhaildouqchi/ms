package org.example.notification;

import lombok.AllArgsConstructor;
import org.example.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(Notification.builder().toCustomerId(notificationRequest.toCustomerId()).toCustomerEmail(notificationRequest.toCustomerEmail()).message(notificationRequest.message()).sender("souhail").sentAt(LocalDateTime.now()).build());
    }
}

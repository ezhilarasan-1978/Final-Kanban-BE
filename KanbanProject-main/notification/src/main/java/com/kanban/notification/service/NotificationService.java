package com.kanban.notification.service;

import com.kanban.notification.config.NotificationDTO;
import com.kanban.notification.domain.Notification;
import com.kanban.notification.repository.INotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    INotificationRepository notificationRepository;
    @Override
    public Notification getNotification(String username) {
        return notificationRepository.findById(username).get();
    }

    @RabbitListener(queues = "user-notification-queue")
    @Override
    public void saveNotification(NotificationDTO notificationDTO) {
        String name=notificationDTO.getJsonObject().get("username").toString();
        Notification notification = new Notification();
        notification.setJsonObject(notificationDTO.getJsonObject());
        notification.setUsername(name);
        notificationRepository.save(notification);
    }
}

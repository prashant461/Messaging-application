package com.messaging.application.notification;

import com.messaging.application.message.Message;
import com.messaging.application.message.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    /* ---------------- MESSAGE / MEDIA ---------------- */

    public void sendMessageNotification(Message message) {

        MessageNotification notification = MessageNotification.builder()
                .chatId(message.getChat().getId())
                .messageId(message.getId())
                .messageType(message.getType())
                .content(message.getContent())
                .mediaUrl(message.getMediaUrl())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .notificationType(
                        message.getType() == MessageType.TEXT
                                ? NotificationType.MESSAGE
                                : NotificationType.MEDIA
                )
                .timestamp(LocalDateTime.now())
                .build();

        sendToUser(message.getReceiverId(), "/queue/messages", notification);
    }

    /* ---------------- SEEN ---------------- */

    public void sendSeenNotification(String chatId, String senderId, String receiverId) {

        MessageNotification notification = MessageNotification.builder()
                .chatId(chatId)
                .senderId(receiverId) // who saw the message
                .receiverId(senderId) // original sender
                .notificationType(NotificationType.SEEN)
                .timestamp(LocalDateTime.now())
                .build();

        sendToUser(senderId, "/queue/seen", notification);
    }

    /* ---------------- CORE SENDER ---------------- */

    private void sendToUser(String userId, String destination, Object payload) {
        try {
            messagingTemplate.convertAndSendToUser(userId, destination, payload);
        } catch (Exception ex) {
            log.error("WebSocket send failed to user {}", userId, ex);
        }
    }
}


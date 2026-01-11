package com.messaging.application.notification;

import com.messaging.application.message.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageNotification {

    private String chatId;
    private Long messageId;
    private MessageType messageType;
    private String content;
    private String senderId;
    private String receiverId;
    private String mediaUrl;
    private NotificationType notificationType;
    private LocalDateTime timestamp;
}


package com.messaging.application.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Builder
public class MessageRequest {
    private String chatId;
    private String senderId;
    private String receiverId;
    private String content;
    private MessageType type;
}


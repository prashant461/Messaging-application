package com.messaging.application.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatResponse {

    private String chatId;
    private String chatName;
    private String targetUserId;
    private boolean isOnline;
    private long unreadCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}


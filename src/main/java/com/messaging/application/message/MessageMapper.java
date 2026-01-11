package com.messaging.application.message;

import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .type(message.getType())
                .state(message.getState())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .mediaUrl(message.getMediaUrl())
                .createdAt(message.getCreatedDate())
                .build();
    }
}


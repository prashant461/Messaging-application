package com.messaging.application.chat;

import com.messaging.application.message.MessageRepository;
import com.messaging.application.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMapper {

    private final MessageRepository messageRepository;

    public ChatResponse toChatResponse(Chat chat, String userId) {

        boolean isSender = chat.getSender().getId().equals(userId);
        User targetUser = isSender ? chat.getRecipient() : chat.getSender();

        return ChatResponse.builder()
                .chatId(chat.getId())
                .chatName(targetUser.getFirstName() + " " + targetUser.getLastName())
                .targetUserId(targetUser.getId())
                .isOnline(targetUser.isUserOnline())
                .unreadCount(messageRepository.countUnread(chat.getId(), userId))
                .lastMessage(messageRepository.findLastMessage(chat.getId()))
                .lastMessageTime(messageRepository.findLastMessageTime(chat.getId()))
                .build();
    }
}


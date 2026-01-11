package com.messaging.application.chat;

import com.messaging.application.exception.BadRequestException;
import com.messaging.application.exception.ChatNotFoundException;
import com.messaging.application.exception.UnauthorizedActionException;
import com.messaging.application.user.User;
import com.messaging.application.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    public String createChat(String senderId, String receiverId) {

        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Cannot create chat with yourself");
        }

        return chatRepository
                .findBetweenUsers(senderId, receiverId)
                .map(Chat::getId)
                .orElseGet(() -> createNewChat(senderId, receiverId));
    }

    private String createNewChat(String senderId, String receiverId) {

        User sender = getUser(senderId);
        User receiver = getUser(receiverId);

        Chat chat = Chat.builder()
                .sender(sender)
                .recipient(receiver)
                .build();

        return chatRepository.save(chat).getId();
    }

    @Transactional(readOnly = true)
    public Page<ChatResponse> getUserChats(String userId, Pageable pageable) {

        return chatRepository.findUserChats(userId, pageable)
                .map(chat -> chatMapper.toChatResponse(chat, userId));
    }

    public void deleteChatForUser(String chatId, String userId) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));

        if (chat.isSender(userId)) {
            chat.markDeletedBySender();
        } else if (chat.isRecipient(userId)) {
            chat.markDeletedByRecipient();
        } else {
            throw new UnauthorizedActionException("User not part of this chat");
        }

        if (chat.isFullyDeleted()) {
            chatRepository.delete(chat);
        }
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }
}


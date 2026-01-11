package com.messaging.application.message;

import com.messaging.application.chat.Chat;
import com.messaging.application.chat.ChatRepository;
import com.messaging.application.exception.ChatNotFoundException;
import com.messaging.application.exception.UnauthorizedMessageActionException;
import com.messaging.application.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final NotificationService notificationService;

    public void sendTextMessage(MessageRequest request) {

        Chat chat = validateChatAccess(request.getChatId(), request.getSenderId());

        Message message = Message.builder()
                .chat(chat)
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .content(request.getContent())
                .type(MessageType.TEXT)
                .state(MessageState.SENT)
                .build();

        messageRepository.save(message);
        notificationService.sendMessageNotification(message);
    }


    public Page<MessageResponse> getChatMessages(String chatId, Pageable pageable) {

        return messageRepository
                .findByChatIdOrderByCreatedDateAsc(chatId, pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    public void markMessagesAsSeen(String chatId, String receiverId) {

        Chat chat = validateChatAccess(chatId, receiverId);

        // Mark DB
        messageRepository.markMessagesAsSeen(chatId, receiverId);

        // Notify original sender
        String senderId = chat.getSender().getId().equals(receiverId)
                ? chat.getRecipient().getId()
                : chat.getSender().getId();

        notificationService.sendSeenNotification(chatId, senderId, receiverId);
    }


    private Chat validateChatAccess(String chatId, String userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));

        if (!chat.isSender(userId) && !chat.isRecipient(userId)) {
            throw new UnauthorizedMessageActionException("Not a chat participant");
        }
        return chat;
    }
}


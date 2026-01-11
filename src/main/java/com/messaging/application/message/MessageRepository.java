package com.messaging.application.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChatIdOrderByCreatedDateAsc(String chatId, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE Message m
        SET m.state = 'SEEN'
        WHERE m.chat.id = :chatId
          AND m.receiverId = :receiverId
          AND m.state = 'SENT'
    """)
    void markMessagesAsSeen(@Param("chatId") String chatId,
                            @Param("receiverId") String receiverId);


    // Count unread messages for a chat for a specific user
    @Query("SELECT COUNT(m) FROM Message m " +
            "WHERE m.chat.id = :chatId " +
            "AND m.receiverId = :userId " +
            "AND m.state = 'SENT'")
    long countUnread(@Param("chatId") String chatId,
                     @Param("userId") String userId);

    // Get the last message content for a chat
    @Query("SELECT m.content FROM Message m " +
            "WHERE m.chat.id = :chatId " +
            "ORDER BY m.createdDate DESC")
    String findLastMessage(@Param("chatId") String chatId);


    // Get last message time for a chat
    @Query("SELECT m.createdDate FROM Message m " +
            "WHERE m.chat.id = :chatId " +
            "ORDER BY m.createdDate DESC")
    List<LocalDateTime> findLastMessageTimeList(@Param("chatId") String chatId, Pageable pageable);

    default LocalDateTime findLastMessageTime(String chatId) {
        List<LocalDateTime> result = findLastMessageTimeList(chatId, PageRequest.of(0,1));
        return result.isEmpty() ? null : result.get(0);
    }
}


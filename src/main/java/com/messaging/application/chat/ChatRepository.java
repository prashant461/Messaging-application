package com.messaging.application.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query("SELECT c FROM Chat c " +
            "WHERE (c.sender.id = :userId AND c.deletedBySender = false) " +
            "OR (c.recipient.id = :userId AND c.deletedByRecipient = false)")
    Page<Chat> findUserChats(@Param("userId") String userId, Pageable pageable);


    @Query("SELECT c FROM Chat c " +
            "WHERE (c.sender.id = :user1 AND c.recipient.id = :user2) " +
            "OR (c.sender.id = :user2 AND c.recipient.id = :user1)")
    Optional<Chat> findBetweenUsers(
            @Param("user1") String user1,
            @Param("user2") String user2
    );
}


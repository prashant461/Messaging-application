package com.messaging.application.message;

import com.messaging.application.chat.Chat;
import com.messaging.application.common.BaseAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Message extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Enumerated(EnumType.STRING)
    private MessageState state;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    private String senderId;
    private String receiverId;

    private String mediaUrl; // URL, not file path
}

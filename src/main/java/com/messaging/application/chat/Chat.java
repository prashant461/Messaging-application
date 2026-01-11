package com.messaging.application.chat;


import com.messaging.application.common.BaseAuditingEntity;
import com.messaging.application.message.Message;
import com.messaging.application.message.MessageState;
import com.messaging.application.message.MessageType;
import com.messaging.application.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User recipient;

    @Builder.Default
    private boolean deletedBySender = false;
    @Builder.Default
    private boolean deletedByRecipient = false;

    public boolean isSender(String userId) {
        return sender.getId().equals(userId);
    }

    public boolean isRecipient(String userId) {
        return recipient.getId().equals(userId);
    }

    public void markDeletedBySender() {
        this.deletedBySender = true;
    }

    public void markDeletedByRecipient() {
        this.deletedByRecipient = true;
    }

    public boolean isFullyDeleted() {
        return deletedBySender && deletedByRecipient;
    }
}


package com.github.hesamjafari06.chat_server.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false,
            unique = true, length = 21)
    private String conversationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    private ConversationType type;

    private Long lastMessageId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onCreate(){
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        conversationId = NanoIdUtils.randomNanoId();
    }

    @PreUpdate
    public void onUpdate(){
        Instant now = Instant.now();
        updatedAt = now;
    }

}

package com.github.hesamjafari06.chat_server.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.hesamjafari06.chat_server.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false,
            unique = true, length = 21)
    private String messageId;

    @ManyToOne
    private ConversationEntity conversation;

    @ManyToOne
    private ConversationMemberEntity sender;

    @ManyToOne
    private MessageEntity replyTo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, updatable = false)
    private Instant sendAt;

    @Column(nullable = false)
    private Instant editedAt;

    private boolean edited = false;

    @PrePersist
    public void onCreate(){
        Instant now = Instant.now();
        sendAt = now;
        editedAt =now;
        messageId = NanoIdUtils.randomNanoId();
    }

    @PreUpdate
    public void onUpdate(){
        Instant now = Instant.now();
        editedAt = now;
        edited = true;
    }
}

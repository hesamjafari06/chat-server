package com.github.hesamjafari06.chat_server.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "conversation_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false,
            unique = true, length = 21)
    private String conversationMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationMemberRole role;

    @Column(nullable = false)
    boolean notificationEnabled;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate(){
        Instant now = Instant.now();
        createdAt = now;
        conversationMemberId = NanoIdUtils.randomNanoId();
    }

}

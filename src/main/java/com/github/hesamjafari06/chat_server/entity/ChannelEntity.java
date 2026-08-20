package com.github.hesamjafari06.chat_server.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.hesamjafari06.chat_server.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "Channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false,
            unique = true, length = 21)
    private String channelId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false, unique = true)
    private ConversationEntity conversation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String publicId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isPrivate;

    @PrePersist
    public void onCreate(){
        Instant now = Instant.now();
        channelId = NanoIdUtils.randomNanoId();
    }
}

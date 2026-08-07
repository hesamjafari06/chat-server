package com.github.hesamjafari06.chat_server.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    private ConversationEntity conversation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String publicId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isPrivate;
}

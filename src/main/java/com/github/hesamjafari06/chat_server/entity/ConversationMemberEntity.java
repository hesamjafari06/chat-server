package com.github.hesamjafari06.chat_server.entity;

import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    private UserEntity user;

    @OneToOne
    private ConversationEntity conversation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationMemberRole role;

    @Column(nullable = false)
    Boolean notificationEnabled;

}

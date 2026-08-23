package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.ConversationMemberNotFoundException;
import com.github.hesamjafari06.chat_server.helper.ConversationMemberHelper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationMemberHelperImpl implements ConversationMemberHelper {

    private final ConversationMemberRepository conversationMemberRepository;

    @Override
    public ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId) {

        return conversationMemberRepository
                .findByConversationMemberId(conversationMemberId)
                .orElseThrow(ConversationMemberNotFoundException::new);
    }

    @Override
    public void save(UserEntity user, ConversationEntity conversation, ConversationMemberRole role, boolean notification) {
        conversationMemberRepository.save(
                ConversationMemberEntity.builder()
                        .user(user)
                        .conversation(conversation)
                        .role(role)
                        .notificationEnabled(notification)
                        .build()
        );
    }

    @Override
    public ConversationMemberEntity saveAndReturn(UserEntity user, ConversationEntity conversation, ConversationMemberRole role, boolean notification) {
        ConversationMemberEntity member =
                ConversationMemberEntity.builder()
                .user(user)
                .conversation(conversation)
                .role(role)
                .notificationEnabled(notification)
                .build();

        conversationMemberRepository.save(member);

        return member;
    }
}

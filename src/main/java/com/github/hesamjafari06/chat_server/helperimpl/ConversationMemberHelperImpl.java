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

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean isConversationMemberJoined(
            ConversationEntity conversation, UserEntity user) {

        return conversationMemberRepository
                .existsByConversationIdAndUserId(
                        conversation.getId(),
                        user.getId()
                );
    }

    @Override
    public ConversationMemberEntity getMemberByUserAndConversation(
            ConversationEntity conversation, UserEntity user) {

        return conversationMemberRepository
                .findByConversationIdAndUserId(
                        conversation.getId(),
                        user.getId()
                ).orElseThrow(ConversationMemberNotFoundException::new);
    }

    @Override
    public void deleteConversationMember(ConversationMemberEntity conversationMember) {

        conversationMemberRepository.delete(conversationMember);
    }

    @Override
    public List<ConversationMemberEntity> getMembersByConversation(ConversationEntity conversation){
        return conversationMemberRepository.findByConversation(conversation);
    }

    @Override
    public void deleteAllConversationMembers(ConversationEntity conversation) {

        conversationMemberRepository.deleteAllByConversation(conversation);
    }

    @Override
    public List<ConversationEntity> findConversationsByUserId(Long userId) {
        return conversationMemberRepository.findConversationsByUserId(userId);
    }

    @Override
    public ConversationMemberEntity findByConversationIdAndUserId(Long conversationId, Long userId){
        return conversationMemberRepository
                .findByConversationIdAndUserId(conversationId, userId)
                .orElseThrow(ConversationMemberNotFoundException::new);
    }

    @Override
    public Optional<ConversationMemberEntity> findPrivateConversationMember(
            String requesterId,
            String targetUserId
    ){
        return conversationMemberRepository.findPrivateConversationMember(requesterId, targetUserId);
    }

    @Override
    public ConversationMemberEntity findOtherMember(ConversationEntity conversation, String userId) {
        return conversationMemberRepository.findOtherMember(conversation, userId)
                .orElseThrow(ConversationMemberNotFoundException::new);
    }
}

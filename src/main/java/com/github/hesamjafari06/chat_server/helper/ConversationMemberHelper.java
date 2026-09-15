package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;

import java.util.List;
import java.util.Optional;

public interface ConversationMemberHelper {

    ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId);

    void save(UserEntity user, ConversationEntity conversation, ConversationMemberRole role, boolean notification);

    ConversationMemberEntity saveAndReturn(
            UserEntity user,
            ConversationEntity conversation,
            ConversationMemberRole role,
            boolean notification);

    boolean isConversationMemberJoined(ConversationEntity conversation, UserEntity user);

    ConversationMemberEntity getMemberByUserAndConversation(ConversationEntity conversation, UserEntity user);

    void deleteConversationMember(ConversationMemberEntity conversationMember);

    List<ConversationMemberEntity> getMembersByConversation(ConversationEntity conversation);

    List<ConversationMemberEntity> findActiveMembers(ConversationEntity conversation);

    void deleteAllConversationMembers(ConversationEntity conversation);

    List<ConversationEntity> findConversationsByUserId(Long userId);

    ConversationMemberEntity findByConversationIdAndUserId(Long conversationId, Long userId);

    Optional<ConversationMemberEntity> findPrivateConversationMember(String requesterId, String targetUserId);

    ConversationMemberEntity findOtherMember(ConversationEntity conversation, String userId);
}

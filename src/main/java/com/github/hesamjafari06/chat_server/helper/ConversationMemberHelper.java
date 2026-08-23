package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;

import java.util.List;

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

    void deleteAllConversationMembers(ConversationEntity conversation);

    List<ConversationEntity> findConversationsByUserId(Long userId);
}

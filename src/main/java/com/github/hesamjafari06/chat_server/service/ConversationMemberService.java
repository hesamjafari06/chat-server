package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

public interface ConversationMemberService {
    ConversationMemberEntity getConversationMemberById(Long id);

    ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId);

    boolean isConversationMemberJoined(ConversationEntity conversation, UserEntity user);

    ConversationMemberEntity getMemberByUserAndConversation(ConversationEntity conversation, UserEntity user);

    void deleteConversationMember(ConversationMemberEntity conversationMember);
}

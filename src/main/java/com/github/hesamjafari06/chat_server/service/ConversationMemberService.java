package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

import java.util.List;

public interface ConversationMemberService {
    ConversationMemberEntity getConversationMemberById(Long id);

    ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId);

    boolean isConversationMemberJoined(ConversationEntity conversation, UserEntity user);

    ConversationMemberEntity getMemberByUserAndConversation(ConversationEntity conversation, UserEntity user);

    void deleteConversationMember(ConversationMemberEntity conversationMember);

    void deleteAllConversationMembers(ConversationEntity conversation);

    ApiResponse<List<ConversationResponse>> getUserConversations();

    String getConversationName(ConversationEntity conversation);
}

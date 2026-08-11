package com.github.hesamjafari06.chat_server.service;


import com.github.hesamjafari06.chat_server.dto.request.ChangeRoleRequest;
import com.github.hesamjafari06.chat_server.dto.request.CreateConversationRequest;
import com.github.hesamjafari06.chat_server.dto.request.JoinConversationRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.ConversationNotFoundException;

public interface ConversationService {
    ApiResponse<ConversationResponse> createConversation(CreateConversationRequest request);

    ConversationEntity getConversationById(Long id);

    ApiResponse<ConversationMemberResponse> joinConversation(JoinConversationRequest request);

    ConversationEntity getConversationByConversationId(String conversationId);

    ApiResponse<ConversationMemberResponse> changeRole(ChangeRoleRequest request);


}
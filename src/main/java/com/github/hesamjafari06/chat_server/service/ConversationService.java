package com.github.hesamjafari06.chat_server.service;


import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.dto.response.LeaveConversationEvent;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.ConversationNotFoundException;

import java.security.Principal;

public interface ConversationService {
    ApiResponse<ConversationResponse> createConversation(String userId);

    ConversationEntity getConversationById(Long id);

    ConversationMemberResponse joinConversation(JoinConversationRequest request, Principal principal);

    ConversationEntity getConversationByConversationId(String conversationId);

    ApiResponse<ConversationMemberResponse> changeRole(ChangeRoleRequest request);

    LeaveConversationEvent leaveConversation(LeaveConversationRequest request, Principal principal);

    ApiResponse<Void> deleteConversation(DeleteConversationRequest request);

    ApiResponse<Void> deleteMember(DeleteMemberRequest request);
}
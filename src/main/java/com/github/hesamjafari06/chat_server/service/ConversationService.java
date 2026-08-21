package com.github.hesamjafari06.chat_server.service;


import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.*;
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

    DeleteConversationEvent deleteConversation(DeleteConversationRequest request, Principal principal);

    DeleteMemberEvent deleteMember(DeleteMemberRequest request, Principal principal);
}
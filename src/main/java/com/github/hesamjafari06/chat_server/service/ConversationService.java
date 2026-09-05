package com.github.hesamjafari06.chat_server.service;


import com.github.hesamjafari06.chat_server.payload.request.*;
import com.github.hesamjafari06.chat_server.payload.response.*;

import java.security.Principal;

public interface ConversationService {
    ApiResponse<ConversationResponse> createConversation(String userId);

    ConversationMemberResponse joinConversation(JoinConversationRequest request, Principal principal);

    ApiResponse<ConversationMemberResponse> changeRole(ChangeRoleRequest request);

    LeaveConversationEvent leaveConversation(LeaveConversationRequest request, Principal principal);

    DeleteConversationEvent deleteConversation(DeleteConversationRequest request, Principal principal);

    DeleteMemberEvent deleteMember(DeleteMemberRequest request, Principal principal);
}
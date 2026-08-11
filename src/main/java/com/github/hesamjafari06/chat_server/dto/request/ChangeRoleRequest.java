package com.github.hesamjafari06.chat_server.dto.request;

import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import lombok.Getter;

@Getter
public class ChangeRoleRequest {
    private String conversationId;
    private String targetMemberId;
    private ConversationMemberRole role;
}

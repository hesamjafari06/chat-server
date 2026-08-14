package com.github.hesamjafari06.chat_server.dto.response;

import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class ConversationMemberResponse {
    private String conversationId;
    private String conversationMemberId;
    private ConversationMemberRole role;
    boolean notificationEnabled;
}

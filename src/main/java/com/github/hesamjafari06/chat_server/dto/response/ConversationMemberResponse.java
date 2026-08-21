package com.github.hesamjafari06.chat_server.dto.response;

import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConversationMemberResponse {
    private String conversationId;
    private String conversationMemberId;
    private String username;
    private ConversationMemberRole role;
    boolean notificationEnabled;
}

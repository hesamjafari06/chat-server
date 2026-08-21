package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteConversationEvent {
    private boolean fullyDeleted;
    private String conversationId;
    private String userId;
    private String username;
    private String conversationMemberId;
}

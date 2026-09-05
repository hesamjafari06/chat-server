package com.github.hesamjafari06.chat_server.payload.request;

import lombok.Getter;

@Getter
public class LeaveConversationRequest {
    private String ConversationId;
    private String MemberId;
}

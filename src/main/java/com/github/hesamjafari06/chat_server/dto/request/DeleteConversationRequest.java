package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class DeleteConversationRequest {
    private String conversationId;
    private boolean keepConversation;
}

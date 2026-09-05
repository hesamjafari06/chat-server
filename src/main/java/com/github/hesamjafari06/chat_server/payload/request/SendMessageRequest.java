package com.github.hesamjafari06.chat_server.payload.request;

import lombok.Getter;

@Getter
public class SendMessageRequest {
    private String conversationId;
    private String content;
    private String replyTo;
}

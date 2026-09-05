package com.github.hesamjafari06.chat_server.payload.request;

import lombok.Getter;

@Getter
public class DeleteMessageRequest {
    private String messageId;
}

package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class UpdateMessageRequest {
    private String messageId;
    private String newContent;
}

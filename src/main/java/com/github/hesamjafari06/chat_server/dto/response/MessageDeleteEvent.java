package com.github.hesamjafari06.chat_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MessageDeleteEvent {
    private String messageId;
    private String conversationId;
}

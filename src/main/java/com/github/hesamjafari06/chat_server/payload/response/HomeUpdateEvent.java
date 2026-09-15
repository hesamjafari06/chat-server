package com.github.hesamjafari06.chat_server.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HomeUpdateEvent {
    private String conversationId;
    private String lastMessage;
}

package com.github.hesamjafari06.chat_server.payload.request;

import lombok.Getter;

@Getter
public class DeleteMemberRequest {
    private String conversationId;
    private String conversationMemberId;
}

package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteMemberEvent {
    private String conversationId;
    private String memberId;
    private String userId;
    private String username;
}

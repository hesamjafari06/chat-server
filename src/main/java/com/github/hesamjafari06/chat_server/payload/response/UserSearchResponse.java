package com.github.hesamjafari06.chat_server.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserSearchResponse {
    private String userId;
    private String username;
}

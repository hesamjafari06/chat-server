package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class GroupResponse {
    private String groupId;
    private String name;
    private String description;
    private boolean isClosed;
}

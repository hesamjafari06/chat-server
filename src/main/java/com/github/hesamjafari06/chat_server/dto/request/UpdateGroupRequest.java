package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class UpdateGroupRequest {
    private String groupId;
    private String name;
    private String description;
    private Boolean isClosed;
}

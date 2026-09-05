package com.github.hesamjafari06.chat_server.payload.request;

import lombok.Getter;

@Getter
public class CreateGroupRequest {
    private String name;
    private String description;
    private boolean closedGroup;
}

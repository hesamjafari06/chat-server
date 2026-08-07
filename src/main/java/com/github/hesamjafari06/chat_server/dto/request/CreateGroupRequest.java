package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequest {
    private String name;
    private String description;
}

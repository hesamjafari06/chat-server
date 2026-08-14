package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class CreateChannelRequest {
    private String name;
    private String publicId;
    private String description;
    private boolean isPrivate;
}

package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChannelRequest {
    private String name;
    private String publicId;
    private String description;
    private boolean isPrivate;
}

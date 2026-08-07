package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChannelResponse {
    private String name;
    private String publicId;
    private String description;
    private boolean isPrivate;
}

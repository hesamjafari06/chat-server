package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class ChannelResponse {
    private String name;
    private String channelId;
    private String publicId;
    private String description;
    private boolean isPrivate;
}

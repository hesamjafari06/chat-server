package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class UpdateChannelRequest {
    private String name;
    private String channelId;
    private String publicId;
    private String description;
    private Boolean isPrivate;
}

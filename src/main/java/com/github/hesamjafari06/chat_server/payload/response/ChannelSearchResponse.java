package com.github.hesamjafari06.chat_server.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChannelSearchResponse {
    private String channelId;
    private String name;
    private String publicId;
}

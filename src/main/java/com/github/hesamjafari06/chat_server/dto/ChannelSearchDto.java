package com.github.hesamjafari06.chat_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChannelSearchDto {
    private String channelId;
    private String name;
    private String publicId;
}

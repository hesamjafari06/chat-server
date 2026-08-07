package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;

public interface ChannelService {
    public ChannelResponse createChannel(CreateChannelRequest request);
    public ChannelEntity getChannelById(Long id);
    public ChannelEntity getChannelByPublicId(String publicId);
}

package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;

public interface ChannelService {
    ApiResponse<ChannelResponse> createChannel(CreateChannelRequest request);

    ChannelEntity getChannelById(Long id);

    ChannelEntity getChannelByPublicId(String publicId);

    ChannelEntity getChannelByChannelId(String channelId);

    ChannelEntity getChannelByConversationId(Long id);

    ChannelEntity getChannelByConversation(ConversationEntity conversation);

    void deleteChannel(ChannelEntity channel);

    ApiResponse<ChannelResponse> updateChannel(UpdateChannelRequest request);
}

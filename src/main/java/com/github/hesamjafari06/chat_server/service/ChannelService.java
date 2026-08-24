package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;

import java.util.List;

public interface ChannelService {
    ApiResponse<ChannelResponse> createChannel(CreateChannelRequest request);

    ApiResponse<ChannelResponse> updateChannel(UpdateChannelRequest request);

    ApiResponse<List<ChannelResponse>> searchChannel(String publicId);
}

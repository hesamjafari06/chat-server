package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.payload.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.payload.request.UpdateChannelRequest;
import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.payload.response.ChannelSearchResponse;

import java.util.List;

public interface ChannelService {
    ApiResponse<ChannelResponse> createChannel(CreateChannelRequest request);

    ApiResponse<ChannelResponse> updateChannel(UpdateChannelRequest request);

    ApiResponse<List<ChannelSearchResponse>> searchChannel(String publicId);
}

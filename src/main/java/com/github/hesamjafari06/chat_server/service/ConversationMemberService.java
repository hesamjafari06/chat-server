package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;

import java.util.List;

public interface ConversationMemberService {

    ApiResponse<List<ConversationResponse>> getUserConversations();

    String getConversationName(ConversationEntity conversation);
}

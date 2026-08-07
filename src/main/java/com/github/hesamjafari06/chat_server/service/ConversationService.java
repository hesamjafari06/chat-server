package com.github.hesamjafari06.chat_server.service;


import com.github.hesamjafari06.chat_server.dto.request.CreateConversationRequest;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;

public interface ConversationService {
    public ConversationResponse createConversation(CreateConversationRequest request);

    public ConversationEntity getConversationById(Long id);
}

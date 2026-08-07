package com.github.hesamjafari06.chat_server.service;


import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;

public interface ConversationService {
    public void createConversation(ConversationType type);

    public ConversationEntity getConversationById(Long id);
}

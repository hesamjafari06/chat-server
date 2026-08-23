package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;

public interface ConversationHelper {

    ConversationEntity getConversationByConversationId(String conversationId);

    void save(ConversationEntity conversation);

    void delete(ConversationEntity conversation);
}

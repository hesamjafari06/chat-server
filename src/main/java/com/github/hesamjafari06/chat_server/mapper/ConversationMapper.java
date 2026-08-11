package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper {
    public ConversationResponse toResponse(ConversationEntity conversation){
        return ConversationResponse.builder()
                .conversationId(conversation.getConversationId())
                .build();
    }
}

package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.repository.MessageRepository;
import com.github.hesamjafari06.chat_server.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConversationMapper {

    private final MessageRepository messageRepository;
    public ConversationResponse toResponse(ConversationEntity conversation, String name){
        return ConversationResponse.builder()
                .conversationId(conversation.getConversationId())
                .name(name)
                .lastMessage(messageRepository.findContentById(conversation.getLastMessageId()).orElse(null))
                .build();
    }
}
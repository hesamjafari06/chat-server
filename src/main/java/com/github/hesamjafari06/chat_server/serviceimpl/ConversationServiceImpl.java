package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.ConversationNotFoundException;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private ConversationRepository conversationRepository;

    @Override
    public void createConversation(ConversationType type) {
        conversationRepository.save(
                ConversationEntity.builder()
                        .type(type)
                        .build()
        );
    }

    @Override
    public ConversationEntity getConversationById(Long id) {
        return conversationRepository.findById(id).orElseThrow(ConversationNotFoundException::new);
    }
}

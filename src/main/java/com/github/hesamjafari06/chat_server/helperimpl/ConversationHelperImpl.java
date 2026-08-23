package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.exception.ConversationNotFoundException;
import com.github.hesamjafari06.chat_server.helper.ConversationHelper;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationHelperImpl implements ConversationHelper {
    private final ConversationRepository conversationRepository;

    @Override
    public ConversationEntity getConversationByConversationId(String conversationId) {
        return conversationRepository.findByConversationId(conversationId)
                .orElseThrow(ConversationNotFoundException::new);
    }

    @Override
    public void save(ConversationEntity conversation) {
        conversationRepository.save(conversation);
    }

    @Override
    public void delete(ConversationEntity conversation) {
        conversationRepository.delete(conversation);
    }
}

package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import com.github.hesamjafari06.chat_server.exception.MessageNotFoundException;
import com.github.hesamjafari06.chat_server.helper.MessageHelper;
import com.github.hesamjafari06.chat_server.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageHelperImpl implements MessageHelper {

    private final MessageRepository messageRepository;

    @Override
    public MessageEntity getMessageByMessageId(String messageId) {
        return messageRepository.findByMessageId(messageId).orElseThrow(MessageNotFoundException::new);
    }

    @Override
    public Optional<MessageEntity> getMessageByPreviousId(Long id) {
        return messageRepository.findByPreviousMessageId(id);
    }

    @Override
    public List<MessageEntity> getConversationMessages(ConversationEntity conversation) {
        return messageRepository.findByConversationOrderBySendAtAsc(conversation);
    }

    @Override
    public void delete(MessageEntity message) {
        messageRepository.delete(message);
    }

    @Override
    public void deleteAllByConversation(ConversationEntity conversation) {
        messageRepository.deleteAllByConversation(conversation);
    }

    @Override
    public void save(MessageEntity message) {
        messageRepository.save(message);
    }

    @Override
    public List<MessageEntity> findByReplyTo(MessageEntity message) {
        return messageRepository.findByReplyTo(message);
    }

    @Override
    public String findContentById(Long id) {
        return messageRepository.findContentById(id).orElse(null);
    }
}
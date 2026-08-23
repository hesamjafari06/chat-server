package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;

import java.util.List;
import java.util.Optional;

public interface MessageHelper {

    MessageEntity getMessageByMessageId(String messageId);

    Optional<MessageEntity> getMessageByPreviousId(Long id);

    List<MessageEntity> getConversationMessages(ConversationEntity conversation);

    void deleteAllByConversation(ConversationEntity conversation);

    void save(MessageEntity message);

    List<MessageEntity> findByReplyTo(MessageEntity message);

    void delete(MessageEntity message);
}

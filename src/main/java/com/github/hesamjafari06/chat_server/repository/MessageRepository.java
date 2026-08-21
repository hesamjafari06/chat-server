package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.dto.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    Optional<MessageEntity> findByMessageId(String messageId);

    @Query("""
            SELECT m.content
            FROM MessageEntity m
            WHERE m.id = :messageId
            """)
    Optional<String> findContentById(Long messageId);

    Optional<MessageEntity> findByPreviousMessageId(Long id);

    List<MessageEntity> findByConversationOrderBySendAtAsc(ConversationEntity conversation);

    List<MessageEntity> findByReplyTo(MessageEntity message);

    void deleteAllByConversation(ConversationEntity conversation);
}

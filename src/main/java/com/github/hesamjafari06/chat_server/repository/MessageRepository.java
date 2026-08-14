package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    Optional<MessageEntity> findByMessageId(String messageId);
}

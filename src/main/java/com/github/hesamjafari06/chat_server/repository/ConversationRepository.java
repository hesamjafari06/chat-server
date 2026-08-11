package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
    Optional<ConversationEntity> findByConversationId(String conversationId);
}

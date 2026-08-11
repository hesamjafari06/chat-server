package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMemberEntity, Long> {

    Optional<ConversationMemberEntity> findByConversationMemberId(String id);

    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);
}

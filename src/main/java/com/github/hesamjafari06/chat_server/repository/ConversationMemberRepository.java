package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMemberEntity, Long> {

    Optional<ConversationMemberEntity> findByConversationMemberId(String id);

    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);

    Optional<ConversationMemberEntity> findByConversationIdAndUserId(Long conversationId, Long userId);

    List<ConversationMemberEntity> findByConversation(ConversationEntity conversation);

    void deleteAllByConversation(ConversationEntity conversation);

    @Query("""
    SELECT cm.conversation
    FROM ConversationMemberEntity cm
    WHERE cm.user.id = :userId
      AND cm.SoftDeleted = false
""")
    List<ConversationEntity> findConversationsByUserId(Long userId);

    @Query("""
    SELECT cm1
    FROM ConversationMemberEntity cm1
    JOIN ConversationMemberEntity cm2
        ON cm1.conversation.id = cm2.conversation.id
    WHERE cm1.user.userId = :requesterId
      AND cm2.user.userId = :targetUserId
      AND cm1.conversation.type =
          com.github.hesamjafari06.chat_server.enums.ConversationType.PRIVATE
""")
    Optional<ConversationMemberEntity> findPrivateConversationMember(
            @Param("requesterId") String requesterId,
            @Param("targetUserId") String targetUserId
    );

    @Query("""
    SELECT cm
    FROM ConversationMemberEntity cm
    WHERE cm.conversation = :conversation
      AND cm.user.userId <> :userId
""")
    Optional<ConversationMemberEntity> findOtherMember(
            @Param("conversation") ConversationEntity conversation,
            @Param("userId") String userId
    );

    @Query("""
    SELECT cm
    FROM ConversationMemberEntity cm
    JOIN FETCH cm.user
    WHERE cm.conversation = :conversation
      AND cm.SoftDeleted = false
""")
    List<ConversationMemberEntity> findActiveMembers(
            ConversationEntity conversation
    );
}


package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    Optional<GroupEntity> findByGroupId(String groupId);
    Optional<GroupEntity> findByConversationId(Long id);
}

package com.github.hesamjafari06.chat_server.repository;

import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {
    boolean existsByPublicId(String publicId);
    Optional<ChannelEntity> findByPublicId(String publicId);
    Optional<ChannelEntity> findByChannelId(String channelId);
}

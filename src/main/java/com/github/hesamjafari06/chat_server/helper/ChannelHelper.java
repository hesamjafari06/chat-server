package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;

import java.util.List;

public interface ChannelHelper {

    ChannelEntity getChannelByChannelId(String channelId);

    ChannelEntity getChannelByConversationId(Long id);

    ChannelEntity getChannelByConversation(ConversationEntity conversation);

    void deleteChannel(ChannelEntity channel);

    boolean existsByPublicId(String publicId);

    void save(ChannelEntity channel);

    List<ChannelEntity> findByPublicIdContaining(String publicId);
}

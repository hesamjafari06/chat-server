package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.exception.ChannelNotFoundException;
import com.github.hesamjafari06.chat_server.helper.ChannelHelper;
import com.github.hesamjafari06.chat_server.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelHelperImpl implements ChannelHelper {

    private final ChannelRepository channelRepository;

    @Override
    public ChannelEntity getChannelByChannelId(String channelId) {
        return channelRepository.findByChannelId(channelId).orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public ChannelEntity getChannelByConversationId(Long id) {
        return channelRepository.findByConversationId(id).orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public ChannelEntity getChannelByConversation(ConversationEntity conversation) {
        return channelRepository.findByConversation(conversation).orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public void deleteChannel(ChannelEntity channel) {
        channelRepository.delete(channel);
    }

    @Override
    public boolean existsByPublicId(String publicId) {
        return channelRepository.existsByPublicId(publicId);
    }

    @Override
    public void save(ChannelEntity channel) {
        channelRepository.save(channel);
    }

    @Override
    public List<ChannelEntity> findByPublicIdContaining(String publicId) {
        return channelRepository.findByPublicIdContaining(publicId);
    }
}

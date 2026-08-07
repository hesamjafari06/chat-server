package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.exception.ChannelNotFoundException;
import com.github.hesamjafari06.chat_server.exception.PublicIdAlreadyExistsException;
import com.github.hesamjafari06.chat_server.mapper.ChannelMapper;
import com.github.hesamjafari06.chat_server.repository.ChannelRepository;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private ChannelRepository channelRepository;
    private ChannelMapper channelMapper;

    @Override
    public ChannelResponse createChannel(CreateChannelRequest request) {
        if (channelRepository.existsByPublicId(request.getPublicId())){
            throw new PublicIdAlreadyExistsException();
        }
        ChannelEntity channel = channelMapper.toEntity(request);
        channelRepository.save(channel);

        return channelMapper.toResponse(channel);
    }

    @Override
    public ChannelEntity getChannelById(Long id) {
        return channelRepository.findById(id).orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public ChannelEntity getChannelByPublicId(String publicId) {
        return channelRepository.findByPublicId(publicId).orElseThrow(ChannelNotFoundException::new);
    }
}

package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.ChannelNotFoundException;
import com.github.hesamjafari06.chat_server.exception.GroupNotFoundException;
import com.github.hesamjafari06.chat_server.exception.PublicIdAlreadyExistsException;
import com.github.hesamjafari06.chat_server.mapper.ChannelMapper;
import com.github.hesamjafari06.chat_server.repository.ChannelRepository;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ConversationMemberRepository conversationMemberRepository;
    private final UserService userService;
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;

    @Override
    @Transactional
    public ApiResponse<ChannelResponse> createChannel(CreateChannelRequest request) {
        if (channelRepository.existsByPublicId(request.getPublicId())){
            throw new PublicIdAlreadyExistsException();
        }

        ChannelEntity channel = channelMapper.toEntity(request);
        channelRepository.save(channel);

        conversationMemberRepository.save(
                ConversationMemberEntity.builder()
                        .conversation(channel.getConversation())
                        .notificationEnabled(true)
                        .role(ConversationMemberRole.OWNER)
                        .user(userService.getCurrentUser())
                        .build()
        );

        return ApiResponse.<ChannelResponse>builder()
                .status("OK")
                .data(channelMapper.toResponse(channel))
                .build();
    }

    @Override
    public ChannelEntity getChannelById(Long id) {
        return channelRepository.findById(id).orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public ChannelEntity getChannelByPublicId(String publicId) {
        return channelRepository.findByPublicId(publicId).orElseThrow(ChannelNotFoundException::new);
    }

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
}

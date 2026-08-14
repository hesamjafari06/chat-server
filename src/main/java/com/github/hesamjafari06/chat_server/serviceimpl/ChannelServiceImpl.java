package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.ChannelNotFoundException;
import com.github.hesamjafari06.chat_server.exception.OnlyOwnerChangeChannelException;
import com.github.hesamjafari06.chat_server.exception.PublicIdAlreadyExistsException;
import com.github.hesamjafari06.chat_server.mapper.ChannelMapper;
import com.github.hesamjafari06.chat_server.repository.ChannelRepository;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberService conversationMemberService;
    private final UserService userService;
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;

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
    @Transactional
    public ApiResponse<ChannelResponse> updateChannel(
            UpdateChannelRequest request) {

        UserEntity user = userService.getCurrentUser();

        ChannelEntity channel =
                getChannelByChannelId(request.getChannelId());

        ConversationMemberEntity member =
                conversationMemberService.getMemberByUserAndConversation(
                        channel.getConversation(),
                        user
                );

        if (member.getRole() != ConversationMemberRole.OWNER) {

            throw new OnlyOwnerChangeChannelException();
        }

        if (request.getPublicId() != null &&
                !Objects.equals(channel.getPublicId(), request.getPublicId())) {

            if (channelRepository.existsByPublicId(
                    request.getPublicId())) {

                throw new PublicIdAlreadyExistsException();
            }

            channel.setPublicId(request.getPublicId());
        }

        if (request.getName() != null && !Objects.equals(channel.getName(), request.getName())) {

            channel.setName(request.getName());
        }

        if (request.getDescription() != null &&
                !Objects.equals(channel.getDescription(), request.getDescription())) {

            channel.setDescription(request.getDescription());
        }

        if (request.getIsPrivate() != null) {

            channel.setPrivate(request.getIsPrivate());
        }

        return ApiResponse.<ChannelResponse>builder()
                .status("OK")
                .data(channelMapper.toResponse(channel))
                .build();
    }
}

package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.payload.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.payload.request.UpdateChannelRequest;
import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.OnlyOwnerChangeChannelException;
import com.github.hesamjafari06.chat_server.exception.PublicIdAlreadyExistsException;
import com.github.hesamjafari06.chat_server.helper.ChannelHelper;
import com.github.hesamjafari06.chat_server.helper.ConversationMemberHelper;
import com.github.hesamjafari06.chat_server.helper.UserHelper;
import com.github.hesamjafari06.chat_server.mapper.ChannelMapper;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ConversationMemberHelper conversationMemberHelper;
    private final UserHelper userHelper;
    private final ChannelHelper channelHelper;
    private final ChannelMapper channelMapper;

    @Override
    @Transactional
    public ApiResponse<ChannelResponse> createChannel(CreateChannelRequest request) {

        if (channelHelper.existsByPublicId(request.getPublicId())){

            throw new PublicIdAlreadyExistsException();
        }

        ChannelEntity channel = channelMapper.toEntity(request);

        channelHelper.save(channel);

        conversationMemberHelper
                .save(userHelper.getCurrentUser(), channel.getConversation(), ConversationMemberRole.OWNER, true);

        return ApiResponse.<ChannelResponse>builder()
                .status("OK")
                .data(channelMapper.toResponse(channel))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<ChannelResponse> updateChannel(
            UpdateChannelRequest request) {

        UserEntity user = userHelper.getCurrentUser();

        ChannelEntity channel = channelHelper.getChannelByChannelId(request.getChannelId());

        ConversationMemberEntity member = conversationMemberHelper.findByConversationIdAndUserId(
                channel.getConversation().getId(),
                user.getId()
        );

        if (member.getRole() != ConversationMemberRole.OWNER) {

            throw new OnlyOwnerChangeChannelException();
        }

        if (request.getPublicId() != null &&
                !Objects.equals(channel.getPublicId(), request.getPublicId())) {

            if (channelHelper.existsByPublicId(request.getPublicId())) {

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

    @Override
    public ApiResponse<List<ChannelResponse>> searchChannel(String publicId){

        List<ChannelResponse> channels =
                channelHelper.findByPublicIdContaining(publicId)
                        .stream()
                        .map(channelMapper::toResponse)
                        .toList();

        return ApiResponse.<List<ChannelResponse>>builder()
                .status("OK")
                .data(channels)
                .build();
    }
}

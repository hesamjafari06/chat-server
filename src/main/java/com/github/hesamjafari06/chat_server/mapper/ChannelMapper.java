package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapper {
    public ChannelEntity toEntity(CreateChannelRequest request){
        return ChannelEntity.builder()
                .name(request.getName())
                .publicId(request.getPublicId())
                .description(request.getDescription())
                .isPrivate(request.isPrivate())
                .conversation(
                        ConversationEntity.builder()
                                .type(ConversationType.CHANNEL)
                                .build()
                )
                .build();
    }

    public ChannelResponse toResponse(ChannelEntity channel){
        return ChannelResponse.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .publicId(channel.getPublicId())
                .description(channel.getDescription())
                .isPrivate(channel.isPrivate())
                .build();
    }
}

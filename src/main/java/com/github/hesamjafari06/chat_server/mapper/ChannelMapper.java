package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.entity.ChannelEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

    private final ConversationRepository conversationRepository;

    public ChannelEntity toEntity(CreateChannelRequest request){

        ConversationEntity conversation =
                ConversationEntity.builder()
                .type(ConversationType.CHANNEL)
                .build();

        conversationRepository.save(conversation);

        return ChannelEntity.builder()
                .name(request.getName())
                .publicId(request.getPublicId())
                .description(request.getDescription())
                .isPrivate(request.isPrivateChannel())
                .conversation(conversation)
                .build();
    }

    public ChannelResponse toResponse(ChannelEntity channel){

        return ChannelResponse.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .publicId(channel.getPublicId())
                .description(channel.getDescription())
                .isPrivate(channel.isPrivate())
                .conversationId(channel.getConversation().getConversationId())
                .build();
    }
}

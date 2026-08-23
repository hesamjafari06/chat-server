package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final ConversationRepository conversationRepository;

    public GroupEntity toEntity(CreateGroupRequest request) {

        ConversationEntity conversation =
                ConversationEntity.builder()
                        .type(ConversationType.GROUP)
                        .build();

        conversationRepository.save(conversation);

        return GroupEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isClosed(request.isClosedGroup())
                .conversation(conversation)
                .build();
    }

    public GroupResponse toResponse(GroupEntity group) {

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .name(group.getName())
                .description(group.getDescription())
                .isClosed(group.isClosed())
                .conversationId(group.getConversation().getConversationId())
                .build();
    }

}

package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    public GroupEntity toEntity(CreateGroupRequest request){
        return GroupEntity.builder()
                .name(request.getName())
                .publicId(request.getPublicId())
                .description(request.getDescription())
                .conversation(
                        ConversationEntity.builder()
                                .type(ConversationType.GROUP)
                                .build()
                )
                .build();
    }

    public GroupResponse toResponse(GroupEntity group){
        return GroupResponse.builder()
                .name(group.getName())
                .publicId(group.getPublicId())
                .description(group.getDescription())
                .build();
    }

}

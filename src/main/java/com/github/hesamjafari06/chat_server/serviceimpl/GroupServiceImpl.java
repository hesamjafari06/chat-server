package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.*;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.OnlyOwnerChangeGroupException;
import com.github.hesamjafari06.chat_server.helper.ConversationMemberHelper;
import com.github.hesamjafari06.chat_server.helper.GroupHelper;
import com.github.hesamjafari06.chat_server.helper.UserHelper;
import com.github.hesamjafari06.chat_server.mapper.GroupMapper;
import com.github.hesamjafari06.chat_server.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final ConversationMemberHelper conversationMemberHelper;
    private final UserHelper userHelper;
    private final GroupHelper groupHelper;

    @Override
    public ApiResponse<GroupResponse> createGroup(CreateGroupRequest request) {

        GroupEntity group = groupMapper.toEntity(request);

        groupHelper.save(group);

        conversationMemberHelper
                .save(userHelper.getCurrentUser(), group.getConversation(), ConversationMemberRole.OWNER, true);

        return ApiResponse.<GroupResponse>builder()
                .status("OK")
                .data(groupMapper.toResponse(group))
                .build();
    }

    @Transactional
    public ApiResponse<GroupResponse> updateGroup(UpdateGroupRequest request){

        UserEntity user = userHelper.getCurrentUser();

        GroupEntity group = groupHelper.getGroupByGroupId(request.getGroupId());

        ConversationMemberEntity member =
                conversationMemberHelper.findByConversationIdAndUserId(
                        group.getConversation().getId(),
                        user.getId()
                );

        if (!member.getRole().equals(ConversationMemberRole.OWNER)){
            throw new OnlyOwnerChangeGroupException();
        }

        if (!Objects.equals(group.getName(), request.getName())){

            group.setName(request.getName());
        }

        if (!Objects.equals(group.getDescription(), request.getDescription())){

            group.setDescription(request.getDescription());
        }

        if (request.getIsClosed() != null){

            group.setClosed(request.getIsClosed());
        }

        return ApiResponse.<GroupResponse>builder()
                .status("OK")
                .data(groupMapper.toResponse(group))
                .build();
    }
}
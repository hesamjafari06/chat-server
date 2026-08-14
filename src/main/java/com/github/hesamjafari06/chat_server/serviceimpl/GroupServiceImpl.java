package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.*;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.GroupNotFoundException;
import com.github.hesamjafari06.chat_server.exception.OnlyOwnerChangeGroupException;
import com.github.hesamjafari06.chat_server.mapper.GroupMapper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.repository.GroupRepository;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import com.github.hesamjafari06.chat_server.service.GroupService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberService conversationMemberService;
    private final UserService userService;

    @Override
    public GroupEntity getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public GroupEntity getGroupByGroupId(String groupId) {
        return groupRepository.findByGroupId(groupId).orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public GroupEntity getGroupByConversationId(Long id) {
        return groupRepository.findByConversationId(id).orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public GroupEntity getGroupByConversation(ConversationEntity conversation) {
        return groupRepository.findByConversation(conversation).orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public void deleteGroup(GroupEntity group) {
        groupRepository.delete(group);
    }

    @Override
    public ApiResponse<GroupResponse> createGroup(CreateGroupRequest request) {

        GroupEntity group = groupMapper.toEntity(request);

        groupRepository.save(group);

        conversationMemberRepository.save(
                ConversationMemberEntity.builder()
                        .conversation(group.getConversation())
                        .notificationEnabled(true)
                        .role(ConversationMemberRole.OWNER)
                        .user(userService.getCurrentUser())
                        .build()
        );

        return ApiResponse.<GroupResponse>builder()
                .status("OK")
                .data(groupMapper.toResponse(group))
                .build();
    }

    @Transactional
    public ApiResponse<GroupResponse> updateGroup(UpdateGroupRequest request){

        UserEntity user = userService.getCurrentUser();

        GroupEntity group = getGroupByGroupId(request.getGroupId());

        ConversationMemberEntity member =
                conversationMemberService.getMemberByUserAndConversation(
                        group.getConversation(),
                        user
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

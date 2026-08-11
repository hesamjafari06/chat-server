package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.exception.GroupNotFoundException;
import com.github.hesamjafari06.chat_server.exception.PublicIdAlreadyExistsException;
import com.github.hesamjafari06.chat_server.mapper.GroupMapper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.repository.GroupRepository;
import com.github.hesamjafari06.chat_server.service.GroupService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ConversationMemberRepository conversationMemberRepository;
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
}

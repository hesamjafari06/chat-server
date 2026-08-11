package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;

public interface GroupService {
    public ApiResponse<GroupResponse> createGroup(CreateGroupRequest request);

    public GroupEntity getGroupById(Long id);

    public GroupEntity getGroupByGroupId(String groupId);

    public GroupEntity getGroupByConversationId(Long id);
}

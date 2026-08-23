package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;

public interface GroupService {
    ApiResponse<GroupResponse> createGroup(CreateGroupRequest request);

    ApiResponse<GroupResponse> updateGroup(UpdateGroupRequest request);
}

package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.payload.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.payload.request.UpdateGroupRequest;
import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.GroupResponse;

public interface GroupService {
    ApiResponse<GroupResponse> createGroup(CreateGroupRequest request);

    ApiResponse<GroupResponse> updateGroup(UpdateGroupRequest request);
}

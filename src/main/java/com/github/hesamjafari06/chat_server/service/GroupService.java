package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;

public interface GroupService {
    public GroupResponse createGroup(CreateGroupRequest request);
    public GroupEntity getGroupById(Long id);
}

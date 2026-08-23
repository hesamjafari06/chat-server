package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;

public interface GroupHelper {

    GroupEntity getGroupByGroupId(String groupId);

    GroupEntity getGroupByConversationId(Long id);

    GroupEntity getGroupByConversation(ConversationEntity conversation);

    void deleteGroup(GroupEntity group);
}

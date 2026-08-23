package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import com.github.hesamjafari06.chat_server.exception.GroupNotFoundException;
import com.github.hesamjafari06.chat_server.helper.GroupHelper;
import com.github.hesamjafari06.chat_server.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupHelperImpl  implements GroupHelper {

    private final GroupRepository groupRepository;

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
    public void delete(GroupEntity group) {
        groupRepository.delete(group);
    }

    @Override
    public void save(GroupEntity group) {
        groupRepository.save(group);
    }
}

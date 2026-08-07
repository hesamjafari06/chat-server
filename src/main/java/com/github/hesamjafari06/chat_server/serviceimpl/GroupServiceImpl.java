package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.entity.GroupEntity;
import com.github.hesamjafari06.chat_server.exception.GroupNotFoundException;
import com.github.hesamjafari06.chat_server.exception.PublicIdAlreadyExistsException;
import com.github.hesamjafari06.chat_server.mapper.GroupMapper;
import com.github.hesamjafari06.chat_server.repository.GroupRepository;
import com.github.hesamjafari06.chat_server.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private GroupMapper groupMapper;

    @Override
    public GroupResponse createGroup(CreateGroupRequest request) {
        if (groupRepository.existsByPublicId(request.getPublicId())){
            throw new PublicIdAlreadyExistsException();
        }
        GroupEntity group = groupMapper.toEntity(request);
        groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    @Override
    public GroupEntity getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public GroupEntity getGroupByPublicId(String publicId) {
        return groupRepository.findByPublicId(publicId).orElseThrow(GroupNotFoundException::new);
    }
}

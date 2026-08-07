package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateConversationRequest;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.ConversationNotFoundException;
import com.github.hesamjafari06.chat_server.mapper.ConversationMapper;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private ConversationRepository conversationRepository;
    private ConversationMapper conversationMapper;
    private UserServiceImpl userService;

    @Override
    public ConversationResponse createConversation(CreateConversationRequest request) {
        ConversationEntity conversation =
                ConversationEntity.builder()
                        .type(ConversationType.PRIVATE)
                        .build();

        ConversationMemberEntity.builder()
                .user(userService.getCurrentUser())
                .conversation(conversation)
                .role(ConversationMemberRole.MEMBER)
                .notificationEnabled(true);

        ConversationMemberEntity.builder()
                .user(userService.findUserByUsername(request.getUsername()))
                .conversation(conversation)
                .role(ConversationMemberRole.MEMBER)
                .notificationEnabled(true);

        return conversationMapper.toResponse(conversation);
    }

    @Override
    public ConversationEntity getConversationById(Long id) {
        return conversationRepository.findById(id).orElseThrow(ConversationNotFoundException::new);
    }
}

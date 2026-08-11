package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.exception.ConversationMemberNotFoundException;
import com.github.hesamjafari06.chat_server.exception.MemberAlreadyJoinedException;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationMemberServiceImpl implements ConversationMemberService {

    private final ConversationMemberRepository conversationMemberRepository;
    private final UserService userService;

    @Override
    public ConversationMemberEntity getConversationMemberById(Long id) {
        return conversationMemberRepository.
                findById(id)
                .orElseThrow(ConversationMemberNotFoundException::new);
    }

    @Override
    public ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId) {
        return conversationMemberRepository
                .findByConversationMemberId(conversationMemberId)
                .orElseThrow(ConversationMemberNotFoundException::new);
    }

//    public boolean isConversationMemberJoined(String conversationId) {
//        UserEntity currentUser = userService.getCurrentUser();
//        ConversationEntity conversation =
//                conversationService.getConversationByConversationId(conversationId);
//
//        return conversationMemberRepository.
//                existsByConversationIdAndUserId(
//                        conversation.getId(), currentUser.getId());
//    }

    public boolean isConversationMemberJoined(
            ConversationEntity conversation) {

        UserEntity currentUser = userService.getCurrentUser();

        return conversationMemberRepository
                .existsByConversationIdAndUserId(
                        conversation.getId(),
                        currentUser.getId()
                );
    }
}

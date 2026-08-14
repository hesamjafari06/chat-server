package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.exception.ConversationMemberNotFoundException;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationMemberServiceImpl implements ConversationMemberService {

    private final ConversationMemberRepository conversationMemberRepository;

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

    public boolean isConversationMemberJoined(
            ConversationEntity conversation, UserEntity user) {

        return conversationMemberRepository
                .existsByConversationIdAndUserId(
                        conversation.getId(),
                        user.getId()
                );
    }

    @Override
    public ConversationMemberEntity getMemberByUserAndConversation(
            ConversationEntity conversation, UserEntity user) {

        return conversationMemberRepository
                .findByConversationIdAndUserId(
                        conversation.getId(),
                        user.getId()
                ).orElseThrow(ConversationMemberNotFoundException::new);
    }

    @Override
    public void deleteConversationMember(ConversationMemberEntity conversationMember) {

        conversationMemberRepository.delete(conversationMember);
    }

    @Override
    public void deleteAllConversationMembers(ConversationEntity conversation) {

        conversationMemberRepository.deleteAllByConversation(conversation);
    }
}

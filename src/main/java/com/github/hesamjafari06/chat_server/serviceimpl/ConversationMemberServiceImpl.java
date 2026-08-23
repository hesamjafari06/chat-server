package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.*;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.ConversationMemberNotFoundException;
import com.github.hesamjafari06.chat_server.helper.ChannelHelper;
import com.github.hesamjafari06.chat_server.helper.UserHelper;
import com.github.hesamjafari06.chat_server.mapper.ConversationMapper;
import com.github.hesamjafari06.chat_server.mapper.ConversationMemberMapper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import com.github.hesamjafari06.chat_server.service.GroupService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemberServiceImpl implements ConversationMemberService {

    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMapper conversationMapper;
    private final GroupService groupService;
    private final ChannelService channelService;
    private final ChannelHelper channelHelper;
    private final UserHelper userHelper;

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

    public List<ConversationMemberEntity> getMembersByConversation(ConversationEntity conversation){
        return conversationMemberRepository.findByConversation(conversation);
    }


    @Override
    public void deleteConversationMember(ConversationMemberEntity conversationMember) {

        conversationMemberRepository.delete(conversationMember);
    }

    @Override
    public void deleteAllConversationMembers(ConversationEntity conversation) {

        conversationMemberRepository.deleteAllByConversation(conversation);
    }

    @Override
    public ApiResponse<List<ConversationResponse>> getUserConversations() {
        UserEntity user = userHelper.getCurrentUser();

        List<ConversationResponse> conversations =
                conversationMemberRepository.findConversationsByUserId(user.getId())
                        .stream()
                        .map(conversation -> conversationMapper.toResponse(conversation,
                                getConversationName(conversation)))
                        .toList();

        return ApiResponse.<List<ConversationResponse>>builder()
                .status("OK")
                .data(conversations)
                .build();
    }

    @Override
    public String getConversationName(ConversationEntity conversation) {

        if (conversation.getType() == ConversationType.GROUP) {

            return groupService.getGroupByConversation(conversation)
                    .getName();

        } else if (conversation.getType() == ConversationType.CHANNEL) {

            return channelHelper.getChannelByConversation(conversation)
                    .getName();

        } else if (conversation.getType() == ConversationType.PRIVATE) {

            UserEntity currentUser = userHelper.getCurrentUser();

            return getMembersByConversation(conversation)
                    .stream()
                    .filter(member ->
                            !member.getUser().getId().equals(currentUser.getId())
                    )
                    .findFirst()
                    .map(member -> member.getUser().getUsername())
                    .orElse(null);
        }
        return null;
    }
}
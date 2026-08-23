package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.*;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.ConversationMemberNotFoundException;
import com.github.hesamjafari06.chat_server.helper.ChannelHelper;
import com.github.hesamjafari06.chat_server.helper.ConversationMemberHelper;
import com.github.hesamjafari06.chat_server.helper.GroupHelper;
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

    private final ConversationMemberHelper conversationMemberHelper;
    private final ConversationMapper conversationMapper;
    private final GroupHelper groupHelper;
    private final ChannelHelper channelHelper;
    private final UserHelper userHelper;

    @Override
    public ApiResponse<List<ConversationResponse>> getUserConversations() {
        UserEntity user = userHelper.getCurrentUser();

        List<ConversationResponse> conversations =
                conversationMemberHelper.findConversationsByUserId(user.getId())
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

            return groupHelper.getGroupByConversation(conversation)
                    .getName();

        } else if (conversation.getType() == ConversationType.CHANNEL) {

            return channelHelper.getChannelByConversation(conversation)
                    .getName();

        } else if (conversation.getType() == ConversationType.PRIVATE) {

            UserEntity currentUser = userHelper.getCurrentUser();

            return conversationMemberHelper.getMembersByConversation(conversation)
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
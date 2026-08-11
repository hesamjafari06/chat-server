package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.CreateConversationRequest;
import com.github.hesamjafari06.chat_server.dto.request.JoinConversationRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.*;
import com.github.hesamjafari06.chat_server.mapper.ConversationMapper;
import com.github.hesamjafari06.chat_server.mapper.ConversationMemberMapper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import com.github.hesamjafari06.chat_server.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationMemberService conversationMemberService;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberMapper conversationMemberMapper;
    private final ConversationMapper conversationMapper;
    private final ChannelService channelService;
    private final UserServiceImpl userService;
    private final GroupService groupService;


    @Override
    public ConversationEntity getConversationById(Long id) {
        return conversationRepository.findById(id).orElseThrow(ConversationNotFoundException::new);
    }

    @Override
    public ConversationEntity getConversationByConversationId(String conversationId) {
        return conversationRepository.findByConversationId(conversationId).orElseThrow(ConversationNotFoundException::new);
    }

    @Override
    @Transactional
    public ApiResponse<ConversationResponse> createConversation(CreateConversationRequest request) {

        UserEntity currentUser = userService.getCurrentUser();
        UserEntity targetUser = userService.findUserByUserId(request.getUserId());

        if (currentUser.getId().equals(targetUser.getId())) {
            throw new InvalidConversationException();
        }

        ConversationEntity conversation =
                ConversationEntity.builder()
                        .type(ConversationType.PRIVATE)
                        .build();

        conversationRepository.save(conversation);


        conversationMemberRepository.save(
                ConversationMemberEntity.builder()
                        .user(currentUser)
                        .conversation(conversation)
                        .role(ConversationMemberRole.MEMBER)
                        .notificationEnabled(true)
                        .build()
        );

        conversationMemberRepository.save(
                ConversationMemberEntity.builder()
                        .user(targetUser)
                        .conversation(conversation)
                        .role(ConversationMemberRole.MEMBER)
                        .notificationEnabled(true)
                        .build()
        );

        return ApiResponse.<ConversationResponse>builder()
                .status("OK")
                .data(conversationMapper.toResponse(conversation))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<ConversationMemberResponse> joinConversation(JoinConversationRequest request){
        ConversationEntity conversation =
                getConversationByConversationId(request.getConversationId());

        UserEntity currentUser = userService.getCurrentUser();

        if (conversation.getType().equals(ConversationType.PRIVATE)){
            throw new JoinPrivateConversationException();
        }

        if (conversationMemberService.isConversationMemberJoined(conversation)){
            throw new MemberAlreadyJoinedException();
        }

        ConversationMemberEntity conversationMember;

        if (conversation.getType().equals(ConversationType.CHANNEL)){
            if(channelService.getChannelByConversationId(conversation.getId()).isPrivate()){
                throw new ChannelIsPrivateException();
            } else {
                conversationMember =
                        ConversationMemberEntity.builder()
                                .user(currentUser)
                                .conversation(conversation)
                                .role(ConversationMemberRole.MEMBER)
                                .notificationEnabled(true)
                                .build();
                conversationMemberRepository.save(conversationMember);

                return ApiResponse.<ConversationMemberResponse>builder()
                        .status("Ok")
                        .data(conversationMemberMapper.toResponse(conversationMember))
                        .build();
            }
        }

        if (conversation.getType().equals(ConversationType.GROUP)){
            if(groupService.getGroupByConversationId(conversation.getId()).isClosed()){
                throw new GroupIsClosedException();
            } else {
                conversationMember =
                        ConversationMemberEntity.builder()
                        .user(currentUser)
                        .conversation(conversation)
                        .role(ConversationMemberRole.MEMBER)
                        .notificationEnabled(true)
                        .build();
                conversationMemberRepository.save(conversationMember);

                return ApiResponse.<ConversationMemberResponse>builder()
                        .status("Ok")
                        .data(conversationMemberMapper.toResponse(conversationMember))
                        .build();
            }
        }
        return null;
    }
}

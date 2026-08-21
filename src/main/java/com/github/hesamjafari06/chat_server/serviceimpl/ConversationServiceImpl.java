package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.*;
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

import java.security.Principal;


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
        return conversationRepository.findByConversationId(conversationId)
                .orElseThrow(ConversationNotFoundException::new);
    }

    @Override
    @Transactional
    public ApiResponse<ConversationResponse> createConversation(String userId) {

        UserEntity currentUser = userService.getCurrentUser();

        UserEntity targetUser = userService.findUserByUserId(userId);

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
                .data(
                        conversationMapper.toResponse(conversation,
                        conversationMemberService.getConversationName(conversation))
                )
                .build();
    }

    @Override
    @Transactional
    public ConversationMemberResponse joinConversation(JoinConversationRequest request, Principal principal) {

        ConversationEntity conversation = getConversationByConversationId(request.getConversationId());

        UserEntity currentUser =
                userService.findUserByUsername(principal.getName());

        if (conversation.getType().equals(ConversationType.PRIVATE)) {

            throw new JoinPrivateConversationException();
        }

        if (conversationMemberService.isConversationMemberJoined(conversation, currentUser)) {

            throw new MemberAlreadyJoinedException();
        }

        ConversationMemberEntity conversationMember;

        if (conversation.getType().equals(ConversationType.CHANNEL)) {

            if (channelService.getChannelByConversationId(conversation.getId()).isPrivate()) {

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

                return conversationMemberMapper.toResponse(conversationMember);
            }
        }

        if (conversation.getType().equals(ConversationType.GROUP)) {

            if (groupService.getGroupByConversationId(conversation.getId()).isClosed()) {

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

                return conversationMemberMapper.toResponse(conversationMember);
            }
        }
        return null;
    }


    @Override
    @Transactional
    public ApiResponse<ConversationMemberResponse> changeRole(ChangeRoleRequest request) {

        UserEntity currentUser = userService.getCurrentUser();

        ConversationEntity conversation =
                getConversationByConversationId(
                        request.getConversationId()
                );

        if (conversation.getType().equals(ConversationType.PRIVATE)) {

            throw new NoRoleInPrivateException();
        }

        if (!conversationMemberService.isConversationMemberJoined(conversation, currentUser)) {

            throw new MemberIsNotJoinedException();
        }

        ConversationMemberEntity currentMember =
                conversationMemberService
                        .getMemberByUserAndConversation(
                                conversation,
                                currentUser
                        );

        if (currentMember.getRole() != ConversationMemberRole.OWNER) {

            throw new NoOwnerChangeRoleException();
        }

        ConversationMemberEntity targetMember =
                conversationMemberService
                        .getConversationMemberByConversationMemberId(request.getTargetMemberId());

        if (currentMember.getId().equals(targetMember.getId())) {

            throw new SelfChangeRoleException();
        }

        if (targetMember.getConversation().equals(conversation)) {

            if (request.getRole().equals(ConversationMemberRole.OWNER)) {

                currentMember.setRole(ConversationMemberRole.ADMIN);
            }

            targetMember.setRole(request.getRole());

            return ApiResponse.<ConversationMemberResponse>builder()
                    .status("OK")
                    .data(conversationMemberMapper.toResponse(targetMember))
                    .build();
        } else {

            throw new MemberIsNotJoinedException();
        }
    }

    @Override
    @Transactional
    public ApiResponse<Void> leaveConversation(LeaveConversationRequest request) {

        UserEntity user = userService.getCurrentUser();

        ConversationEntity conversation = getConversationByConversationId(request.getConversationId());

        ConversationMemberEntity conversationMember =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        user
                );

        if (conversationMember.getRole().equals(ConversationMemberRole.OWNER)) {

            throw new OwnerCantLeaveException();
        }

        conversationMemberService.deleteConversationMember(conversationMember);

        return ApiResponse.<Void>builder()
                .status("OK")
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteConversation(
            DeleteConversationRequest request) {

        UserEntity user = userService.getCurrentUser();

        ConversationEntity conversation =
                getConversationByConversationId(
                        request.getConversationId()
                );

        ConversationType type = conversation.getType();

        ConversationMemberEntity member =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        user
                );

        if (type == ConversationType.PRIVATE && request.isKeepConversation()) {

            conversationMemberService.deleteConversationMember(member);

        } else {

            if (type != ConversationType.PRIVATE && member.getRole() != ConversationMemberRole.OWNER) {

                throw new OnlyOwnerCanDeleteException();
            }

            if (type == ConversationType.GROUP) {

                GroupEntity group =
                        groupService.getGroupByConversation(conversation);

                groupService.deleteGroup(group);

            } else if (type == ConversationType.CHANNEL) {

                ChannelEntity channel =
                        channelService.getChannelByConversation(conversation);

                channelService.deleteChannel(channel);
            }

            conversationMemberService
                    .deleteAllConversationMembers(conversation);

            conversationRepository.delete(conversation);
        }

        return ApiResponse.<Void>builder()
                .status("OK")
                .build();
    }

    @Override
    public ApiResponse<Void> deleteMember(DeleteMemberRequest request) {

        UserEntity user =
                userService.getCurrentUser();

        ConversationEntity conversation =
                getConversationByConversationId(request.getConversationId());

        ConversationMemberEntity currentMember =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        user
                );

        if (currentMember.getRole().equals(ConversationMemberRole.MEMBER)) {
            throw new MemberCanNotDeleteMemberException();
        }

        ConversationMemberEntity targetMember =
                conversationMemberService.getConversationMemberByConversationMemberId(
                        request.getConversationMemberId()
                );

        if (!targetMember.getConversation().equals(conversation)) {
            throw new MemberIsNotJoinedException();
        }

        if (targetMember.getRole().equals(ConversationMemberRole.OWNER)) {
            throw new CanNotDeleteOwnerException();
        }

        if (targetMember.getRole().equals(ConversationMemberRole.ADMIN) &&
                currentMember.getRole().equals(ConversationMemberRole.ADMIN)) {

            throw new AdminCanNotDeleteAdminException();
        }

        conversationMemberService.deleteConversationMember(targetMember);

        return ApiResponse.<Void>builder()
                .status("OK")
                .build();
    }

}

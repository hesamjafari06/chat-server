package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.*;
import com.github.hesamjafari06.chat_server.entity.*;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.*;
import com.github.hesamjafari06.chat_server.helper.*;
import com.github.hesamjafari06.chat_server.mapper.ConversationMapper;
import com.github.hesamjafari06.chat_server.mapper.ConversationMemberMapper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import com.github.hesamjafari06.chat_server.repository.ConversationRepository;
import com.github.hesamjafari06.chat_server.repository.MessageRepository;
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
    private final ConversationHelper conversationHelper;
    private final ConversationMemberService conversationMemberService;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberHelper conversationMemberHelper;
    private final ConversationMemberMapper conversationMemberMapper;
    private final MessageRepository messageRepository;
    private final ConversationMapper conversationMapper;
    private final ChannelHelper channelHelper;
    private final UserHelper userHelper;
    private final GroupService groupService;
    private final GroupHelper groupHelper;


    @Override
    public ConversationEntity getConversationByConversationId(String conversationId) {
        return conversationRepository.findByConversationId(conversationId)
                .orElseThrow(ConversationNotFoundException::new);
    }

    @Override
    @Transactional
    public ApiResponse<ConversationResponse> createConversation(String userId) {

        UserEntity currentUser = userHelper.getCurrentUser();

        UserEntity targetUser = userHelper.findUserByUserId(userId);

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

        ConversationEntity conversation =
                conversationHelper.getConversationByConversationId(request.getConversationId());

        UserEntity currentUser = userHelper.findUserByUsername(principal.getName());

        if (conversation.getType().equals(ConversationType.PRIVATE)) {

            throw new JoinPrivateConversationException();
        }

        if (conversationMemberService.isConversationMemberJoined(conversation, currentUser)) {

            throw new MemberAlreadyJoinedException();
        }

        ConversationMemberEntity conversationMember;

        if (conversation.getType().equals(ConversationType.CHANNEL)) {

            if (channelHelper.getChannelByConversationId(conversation.getId()).isPrivate()) {

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

            if (groupHelper.getGroupByConversationId(conversation.getId()).isClosed()) {

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

        UserEntity currentUser = userHelper.getCurrentUser();

        ConversationEntity conversation =
                conversationHelper.getConversationByConversationId(
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
                conversationMemberHelper.getConversationMemberByConversationMemberId(request.getTargetMemberId());

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
    public LeaveConversationEvent leaveConversation(LeaveConversationRequest request, Principal principal) {

        UserEntity user = userHelper.findUserByUsername(principal.getName());

        ConversationEntity conversation =
                conversationHelper.getConversationByConversationId(request.getConversationId());

        ConversationMemberEntity conversationMember =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        user
                );

        if (conversationMember.getRole().equals(ConversationMemberRole.OWNER)) {

            throw new OwnerCantLeaveException();
        }

        LeaveConversationEvent event =
                conversationMemberMapper.toLeaveEvent(conversationMember);

        conversationMemberService.deleteConversationMember(conversationMember);

        return event;
    }

    @Override
    @Transactional
    public DeleteConversationEvent deleteConversation(DeleteConversationRequest request, Principal principal) {

        UserEntity user = userHelper.findUserByUsername(principal.getName());

        ConversationEntity conversation =
                conversationHelper.getConversationByConversationId(
                        request.getConversationId()
                );

        ConversationType type = conversation.getType();

        ConversationMemberEntity member =
                conversationMemberService.getMemberByUserAndConversation(conversation, user);

        if (type == ConversationType.PRIVATE && request.isKeepConversation()) {

            DeleteConversationEvent event = conversationMapper.toDeleteEvent(conversation, true, member);

            conversationMemberService.deleteConversationMember(member);

            return event;
        }

        if (type != ConversationType.PRIVATE && member.getRole() != ConversationMemberRole.OWNER) {

            throw new OnlyOwnerCanDeleteException();
        }

        if (type == ConversationType.GROUP) {

            GroupEntity group = groupHelper.getGroupByConversation(conversation);

            groupHelper.deleteGroup(group);

        } else if (type == ConversationType.CHANNEL) {

            ChannelEntity channel = channelHelper.getChannelByConversation(conversation);

            channelHelper.deleteChannel(channel);
        }


        DeleteConversationEvent event = conversationMapper.toDeleteEvent(conversation, false, member);

        messageRepository.deleteAllByConversation(conversation);

        conversationMemberService.deleteAllConversationMembers(conversation);

        conversationRepository.delete(conversation);


        return event;
    }

    @Override
    @Transactional
    public DeleteMemberEvent deleteMember(DeleteMemberRequest request, Principal principal) {

        UserEntity user = userHelper.findUserByUsername(principal.getName());

        ConversationEntity conversation =
                conversationHelper.getConversationByConversationId(request.getConversationId());

        ConversationMemberEntity currentMember =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        user
                );

        if (currentMember.getRole().equals(ConversationMemberRole.MEMBER)) {
            throw new MemberCanNotDeleteMemberException();
        }

        ConversationMemberEntity targetMember =
                conversationMemberHelper.getConversationMemberByConversationMemberId(
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

        DeleteMemberEvent event =
                conversationMemberMapper.toDeleteEvent(targetMember);

        conversationMemberService.deleteConversationMember(targetMember);

        return event;
    }

}

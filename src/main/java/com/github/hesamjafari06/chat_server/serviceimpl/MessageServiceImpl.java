package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.payload.request.DeleteMessageRequest;
import com.github.hesamjafari06.chat_server.payload.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.payload.request.UpdateMessageRequest;
import com.github.hesamjafari06.chat_server.payload.response.HomeUpdateEvent;
import com.github.hesamjafari06.chat_server.payload.response.MessageDeleteEvent;
import com.github.hesamjafari06.chat_server.payload.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.*;
import com.github.hesamjafari06.chat_server.helper.ConversationHelper;
import com.github.hesamjafari06.chat_server.helper.ConversationMemberHelper;
import com.github.hesamjafari06.chat_server.helper.MessageHelper;
import com.github.hesamjafari06.chat_server.helper.UserHelper;
import com.github.hesamjafari06.chat_server.mapper.MessageMapper;
import com.github.hesamjafari06.chat_server.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserHelper userHelper;
    private final ConversationHelper conversationHelper;
    private final ConversationMemberHelper conversationMemberHelper;
    private final MessageMapper messageMapper;
    private final MessageHelper messageHelper;


    @Override
    public List<MessageResponse> getConversationMessages(ConversationEntity conversation) {
        return messageHelper.getConversationMessages(conversation).stream().map(messageMapper::toResponse).toList();
    }

    private void updateHome(
            ConversationEntity conversation,
            HomeUpdateEvent event
    ) {

        List<ConversationMemberEntity> members =
                conversationMemberHelper
                        .findActiveMembers(conversation);

        for (ConversationMemberEntity member : members) {

            String username =
                    member.getUser().getUsername();

            messagingTemplate.convertAndSendToUser(
                    username,
                    "/queue/home",
                    event
            );
        }
    }

    @Override
    @Transactional
    public MessageResponse sendMessage(
            SendMessageRequest request,
            Principal principal
    ) {

        UserEntity currentUser = userHelper.findUserByUsername(principal.getName());

        ConversationEntity conversation =
                conversationHelper.getConversationByConversationId(
                        request.getConversationId()
                );

        ConversationMemberEntity currentMember =
                conversationMemberHelper.getMemberByUserAndConversation(conversation, currentUser);

        if (conversation.getType() == ConversationType.CHANNEL
                && currentMember.getRole() == ConversationMemberRole.MEMBER) {

            throw new MemberCanNotSendChannelException();
        }

        MessageEntity replyMessage = null;

        if (request.getReplyTo() != null) {

            replyMessage = messageHelper.getMessageByMessageId(request.getReplyTo());

            if (!replyMessage.getConversation().equals(conversation)) {
                throw new ReplyOtherConversationException();
            }
        }

        MessageEntity message =
                messageMapper.toEntity(
                        request,
                        conversation,
                        currentMember,
                        replyMessage,
                        conversation.getLastMessageId()
                );

        messageHelper.save(message);

        conversation.setLastMessageId(message.getId());

        MessageResponse response = messageMapper.toResponse(message);

        HomeUpdateEvent event = HomeUpdateEvent.builder()
                .conversationId(conversation.getConversationId())
                .lastMessage(response.getContent())
                .build();

        updateHome(conversation, event);

        return response;
    }

    @Override
    @Transactional
    public MessageResponse updateMessage(UpdateMessageRequest request, Principal principal){

        UserEntity user = userHelper.findUserByUsername(principal.getName());

        MessageEntity message = messageHelper.getMessageByMessageId(request.getMessageId());

        ConversationMemberEntity member = message.getSender();

        if (!member.getUser().equals(user)){

            throw new NotMemberMessageException();
        }

        message.setContent(request.getNewContent());

        return messageMapper.toResponse(message);
    }

    @Override
    @Transactional
    public MessageDeleteEvent deleteMessage(DeleteMessageRequest request, Principal principal){

        UserEntity user = userHelper.findUserByUsername(principal.getName());

        MessageEntity message = messageHelper.getMessageByMessageId(request.getMessageId());

        ConversationEntity conversation = message.getConversation();

        ConversationMemberEntity currentMember =
                conversationMemberHelper.getMemberByUserAndConversation(conversation, user);

        ConversationMemberEntity targetMember =
                message.getSender();

        if (!(targetMember.getId().equals(currentMember.getId()))){

            if (conversation.getType() == ConversationType.PRIVATE ||
                    currentMember.getRole().equals(ConversationMemberRole.MEMBER)) {

                throw new MemberCanNotDeleteOtherMessageException();
            }

            if (currentMember.getRole().equals(ConversationMemberRole.ADMIN) &&
                    !targetMember.getRole().equals(ConversationMemberRole.MEMBER)){

                throw new AdminCanOnlyDeleteMemberMessageException();
            }
        }

        messageHelper.getMessageByPreviousId(message.getId())
                .ifPresent(nextMessage ->
                        nextMessage.setPreviousMessageId(
                                message.getPreviousMessageId()
                        )
                );

        if (Objects.equals(conversation.getLastMessageId(), message.getId())) {
            conversation.setLastMessageId(message.getPreviousMessageId());
        }

        messageHelper.findByReplyTo(message)
                .forEach(reply -> reply.setReplyTo(null));

        messageHelper.delete(message);

        return messageMapper.toDeleteEvent(message);
    }
}

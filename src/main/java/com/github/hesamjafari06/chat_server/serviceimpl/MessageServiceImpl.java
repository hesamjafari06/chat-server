package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.DeleteMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateMessageRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.enums.ConversationMemberRole;
import com.github.hesamjafari06.chat_server.enums.ConversationType;
import com.github.hesamjafari06.chat_server.exception.*;
import com.github.hesamjafari06.chat_server.mapper.MessageMapper;
import com.github.hesamjafari06.chat_server.repository.MessageRepository;
import com.github.hesamjafari06.chat_server.service.ConversationMemberService;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import com.github.hesamjafari06.chat_server.service.MessageService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserService userService;
    private final ConversationService conversationService;
    private final MessageRepository messageRepository;
    private final ConversationMemberService conversationMemberService;
    private final MessageMapper messageMapper;

    @Override
    public MessageEntity getMessageByMessageId(String messageId) {
        return messageRepository.findByMessageId(messageId).orElseThrow(MessageNotFoundException::new);
    }

    public Optional<MessageEntity> getMessageByPreviousId(Long id) {
        return messageRepository.findByPreviousMessageId(id);
    }

    @Override
    public String getLastMessageContent(ConversationEntity conversation) {
        return messageRepository.findContentById(conversation.getLastMessageId()).orElse(null);
    }

    public List<MessageResponse> getConversationMessages(ConversationEntity conversation) {
        return messageRepository.findByConversationOrderBySendAtAsc(conversation)
                .stream().map(messageMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public MessageResponse sendMessage(
            SendMessageRequest request,
            Principal principal
    ) {

        UserEntity currentUser =
                userService.findUserByUsername(principal.getName());

        ConversationEntity conversation =
                conversationService.getConversationByConversationId(
                        request.getConversationId()
                );

        ConversationMemberEntity currentMember =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        currentUser
                );

        if (conversation.getType() == ConversationType.CHANNEL
                && currentMember.getRole() == ConversationMemberRole.MEMBER) {

            throw new MemberCanNotSendChannelException();
        }

        MessageEntity replyMessage = null;

        if (request.getReplyTo() != null) {

            replyMessage = getMessageByMessageId(request.getReplyTo());

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

        messageRepository.save(message);

        conversation.setLastMessageId(message.getId());

        return messageMapper.toResponse(message);
    }

    @Override
    @Transactional
    public ApiResponse<MessageResponse> updateMessage(UpdateMessageRequest request){

        UserEntity user = userService.getCurrentUser();

        MessageEntity message = getMessageByMessageId(request.getMessageId());

        ConversationMemberEntity member = message.getSender();

        if (!member.getUser().equals(user)){

            throw new NotMemberMessageException();
        }

        message.setContent(request.getNewContent());

        return ApiResponse.<MessageResponse>builder()
                .status("OK")
                .data(messageMapper.toResponse(message))
                .build();
    }

    public  ApiResponse<Void> deleteMessage(DeleteMessageRequest request){

        UserEntity user = userService.getCurrentUser();

        MessageEntity message = getMessageByMessageId(request.getMessageId());

        ConversationEntity conversation = message.getConversation();

        ConversationMemberEntity currentMember =
                conversationMemberService.getMemberByUserAndConversation(
                        conversation,
                        user
                );

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

        getMessageByPreviousId(message.getId())
                .ifPresent(nextMessage ->
                        nextMessage.setPreviousMessageId(
                                message.getPreviousMessageId()
                        )
                );

        if (Objects.equals(conversation.getLastMessageId(), message.getId())) {
            conversation.setLastMessageId(message.getPreviousMessageId());
        }

        messageRepository.delete(message);

        return ApiResponse.<Void>builder()
                .status("OK")
                .build();
    }
}

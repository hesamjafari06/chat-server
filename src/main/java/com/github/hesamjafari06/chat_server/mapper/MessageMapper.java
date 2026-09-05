package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.payload.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.payload.response.MessageDeleteEvent;
import com.github.hesamjafari06.chat_server.payload.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    public MessageEntity toEntity(SendMessageRequest request, ConversationEntity conversation,
            ConversationMemberEntity sender, MessageEntity replyMessage, Long previousMessageId) {

        return MessageEntity.builder()
                .conversation(conversation)
                .sender(sender)
                .content(request.getContent())
                .replyTo(replyMessage)
                .previousMessageId(previousMessageId)
                .build();
    }

    public MessageResponse toResponse(MessageEntity message) {

        MessageEntity reply = message.getReplyTo();

        return MessageResponse.builder()
                .messageId(message.getMessageId())
                .conversationId(message.getConversation().getConversationId())
                .senderId(message.getSender().getConversationMemberId())
                .senderUsername(message.getSender().getUser().getUsername())
                .replyTo(reply != null ? reply.getMessageId() : null)
                .replyContent(reply != null ? reply.getContent() : null)
                .sendAt(message.getSendAt())
                .content(message.getContent())
                .isEdited(message.isEdited())
                .editedAt(message.getEditedAt())
                .senderRole(message.getSender().getRole())
                .build();
    }

    public MessageDeleteEvent toDeleteEvent(MessageEntity message) {
        return MessageDeleteEvent.builder()
                .conversationId(message.getConversation().getConversationId())
                .messageId(message.getMessageId())
                .build();
    }

}

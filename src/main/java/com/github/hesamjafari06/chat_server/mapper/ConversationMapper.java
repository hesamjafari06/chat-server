package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.payload.request.DeleteConversationEvent;
import com.github.hesamjafari06.chat_server.payload.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.helper.MessageHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConversationMapper {

    private final MessageHelper messageHelper;

    public ConversationResponse toResponse(ConversationEntity conversation, String name){
        return ConversationResponse.builder()
                .conversationId(conversation.getConversationId())
                .name(name)
                .lastMessage(messageHelper.findContentById(conversation.getLastMessageId()))
                .build();
    }

    public DeleteConversationEvent toDeleteEvent(ConversationEntity conversation,
                                                 boolean fullyDeleted,
                                                 ConversationMemberEntity conversationMember
    ) {
        return DeleteConversationEvent.builder()
                .fullyDeleted(fullyDeleted)
                .conversationMemberId(conversationMember.getConversationMemberId())
                .conversationId(conversation.getConversationId())
                .username(conversationMember.getUser().getUsername())
                .userId(conversationMember.getUser().getUserId())
                .build();
    }
}
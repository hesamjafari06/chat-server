package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import org.springframework.stereotype.Component;

@Component
public class ConversationMemberMapper {

    public ConversationMemberResponse toResponse(ConversationMemberEntity conversationMember){
        return ConversationMemberResponse.builder()
                .conversationId(conversationMember.getConversation().getConversationId())
                .conversationMemberId(conversationMember.getConversationMemberId())
                .role(conversationMember.getRole())
                .notificationEnabled(conversationMember.isNotificationEnabled())
                .build();
    }
}

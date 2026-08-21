package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.dto.response.LeaveConversationEvent;
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
                .username(conversationMember.getUser().getUsername())
                .build();
    }

    public LeaveConversationEvent toLeaveEvent(ConversationMemberEntity conversationMember) {
        return LeaveConversationEvent.builder()
                .memberId(conversationMember.getConversationMemberId())
                .userId(conversationMember.getUser().getUserId())
                .username(conversationMember.getUser().getUsername())
                .conversationId(conversationMember.getConversation().getConversationId())
                .build();
    }
}

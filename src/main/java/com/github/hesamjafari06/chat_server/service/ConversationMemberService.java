package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

public interface ConversationMemberService {
    public ConversationMemberEntity getConversationMemberById(Long id);

    public ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId);

    public boolean isConversationMemberJoined(ConversationEntity conversation, UserEntity user);

    public ConversationMemberEntity getMemberByUserIdAndConversationId(ConversationEntity conversation);
}

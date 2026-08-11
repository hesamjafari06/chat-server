package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;

public interface ConversationMemberService {
    public ConversationMemberEntity getConversationMemberById(Long id);

    public ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId);

    public boolean isConversationMemberJoined(ConversationEntity conversation);
}

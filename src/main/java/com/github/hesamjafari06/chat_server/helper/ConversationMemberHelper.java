package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;

public interface ConversationMemberHelper {

    ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId);

}

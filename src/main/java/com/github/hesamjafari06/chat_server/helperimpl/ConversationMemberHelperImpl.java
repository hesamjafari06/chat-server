package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.ConversationMemberEntity;
import com.github.hesamjafari06.chat_server.exception.ConversationMemberNotFoundException;
import com.github.hesamjafari06.chat_server.helper.ConversationMemberHelper;
import com.github.hesamjafari06.chat_server.repository.ConversationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationMemberHelperImpl implements ConversationMemberHelper {

    private final ConversationMemberRepository conversationMemberRepository;

    @Override
    public ConversationMemberEntity getConversationMemberByConversationMemberId(String conversationMemberId) {

        return conversationMemberRepository
                .findByConversationMemberId(conversationMemberId)
                .orElseThrow(ConversationMemberNotFoundException::new);
    }
}

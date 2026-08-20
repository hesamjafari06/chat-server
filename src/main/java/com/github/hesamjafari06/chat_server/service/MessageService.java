package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.DeleteMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateMessageRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;

import java.util.Optional;

public interface MessageService {
    MessageEntity getMessageByMessageId(String messageId);

    ApiResponse<MessageResponse> sendMessage(SendMessageRequest request);

    ApiResponse<MessageResponse> updateMessage(UpdateMessageRequest request);

    String getLastMessageContent(ConversationEntity conversation);

    Optional<MessageEntity> getMessageByPreviousId(Long id);

    ApiResponse<Void> deleteMessage(DeleteMessageRequest request);
}

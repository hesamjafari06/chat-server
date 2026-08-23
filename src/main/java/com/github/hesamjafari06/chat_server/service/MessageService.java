package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.DeleteMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateMessageRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.MessageDeleteEvent;
import com.github.hesamjafari06.chat_server.dto.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.entity.MessageEntity;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface MessageService {

    MessageResponse sendMessage(SendMessageRequest request, Principal principal);

    MessageResponse updateMessage(UpdateMessageRequest request, Principal principal);

    MessageDeleteEvent deleteMessage(DeleteMessageRequest request, Principal principal);

    List<MessageResponse> getConversationMessages(ConversationEntity conversation);
}

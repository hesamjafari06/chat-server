package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.payload.request.DeleteMessageRequest;
import com.github.hesamjafari06.chat_server.payload.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.payload.request.UpdateMessageRequest;
import com.github.hesamjafari06.chat_server.payload.response.MessageDeleteEvent;
import com.github.hesamjafari06.chat_server.payload.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;

import java.security.Principal;
import java.util.List;

public interface MessageService {

    MessageResponse sendMessage(SendMessageRequest request, Principal principal);

    MessageResponse updateMessage(UpdateMessageRequest request, Principal principal);

    MessageDeleteEvent deleteMessage(DeleteMessageRequest request, Principal principal);

    List<MessageResponse> getConversationMessages(ConversationEntity conversation);
}

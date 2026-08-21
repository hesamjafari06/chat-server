package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.DeleteMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.JoinConversationRequest;
import com.github.hesamjafari06.chat_server.dto.request.SendMessageRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateMessageRequest;
import com.github.hesamjafari06.chat_server.dto.response.ConversationMemberResponse;
import com.github.hesamjafari06.chat_server.dto.response.MessageDeleteEvent;
import com.github.hesamjafari06.chat_server.dto.response.MessageResponse;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import com.github.hesamjafari06.chat_server.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final ConversationService conversationService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send.message")
    public void sendMessage(
            SendMessageRequest request,
            Principal principal
    ) {

        MessageResponse response =
                messageService.sendMessage(
                        request,
                        principal
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/"
                        + request.getConversationId(),
                response
        );
    }

    @MessageMapping("/delete.message")
    public void deleteMessage(
            DeleteMessageRequest request,
            Principal principal
    ) {

        MessageDeleteEvent event =
                messageService.deleteMessage(
                        request,
                        principal
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + event.getConversationId(),
                event
        );
    }

    @MessageMapping("/edit.message")
    public void editMessage(
            UpdateMessageRequest request,
            Principal principal
    ) {

        System.out.println("EDIT REQUEST: "
                + request.getMessageId());

        MessageResponse response =
                messageService.updateMessage(
                        request,
                        principal
                );

        System.out.println("EDIT RESPONSE: "
                + response);

        messagingTemplate.convertAndSend(
                "/topic/chat/" +
                        response.getConversationId(),
                response
        );
    }

    @MessageMapping("/join.conversation")
    public void joinConversation(
            JoinConversationRequest request,
            Principal principal
    ) {

        ConversationMemberResponse response =
                conversationService.joinConversation(
                        request,
                        principal
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/" +
                        request.getConversationId(),
                response
        );
    }
}
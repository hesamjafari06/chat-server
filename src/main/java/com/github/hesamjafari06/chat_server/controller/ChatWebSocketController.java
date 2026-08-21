package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.*;
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

    @MessageMapping("/leave.conversation")
    public void leaveConversation(
            LeaveConversationRequest request,
            Principal principal
    ) {

        LeaveConversationEvent event =
                conversationService.leaveConversation(
                        request,
                        principal
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + event.getConversationId(),
                event
        );
    }

    @MessageMapping("/delete.member")
    public void deleteMember(
            DeleteMemberRequest request,
            Principal principal
    ) {

        DeleteMemberEvent event =
                conversationService.deleteMember(
                        request,
                        principal
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + event.getConversationId(),
                event
        );
    }

    @MessageMapping("/delete.conversation")
    public void deleteConversation(
            DeleteConversationRequest request,
            Principal principal
    ) {

        DeleteConversationEvent event =
                conversationService.deleteConversation(
                        request,
                        principal
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + event.getConversationId(),
                event
        );
    }
}
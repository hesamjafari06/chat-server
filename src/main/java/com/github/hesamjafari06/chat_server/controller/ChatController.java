package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.MessageResponse;
import com.github.hesamjafari06.chat_server.entity.ConversationEntity;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import com.github.hesamjafari06.chat_server.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final ConversationService conversationService;

    @GetMapping("/{chatId}/messages")
    public ApiResponse<List<MessageResponse>> getMessages(
            @PathVariable String chatId
    ) {

        ConversationEntity conversation =
                conversationService
                        .getConversationByConversationId(chatId);

        return ApiResponse.<List<MessageResponse>>builder()
                .status("OK")
                .data(messageService.getConversationMessages(conversation))
                .build();
    }
}

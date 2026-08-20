package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ConversationResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.service.ConversationService;
import com.github.hesamjafari06.chat_server.service.UserService;
import com.github.hesamjafari06.chat_server.serviceimpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final ConversationService conversationService;

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getProfile(@PathVariable String userId){
        return userService.getUserProfile(userId);

    }

    @PostMapping("/{userId}")
    public ApiResponse<ConversationResponse> createConversation(@PathVariable String userId) {
        return conversationService.createConversation(userId);
    }
}
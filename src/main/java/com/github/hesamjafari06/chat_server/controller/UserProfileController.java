package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
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

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getProfile(@PathVariable String userId){
        return userService.getUserProfile(userId);

    }
}

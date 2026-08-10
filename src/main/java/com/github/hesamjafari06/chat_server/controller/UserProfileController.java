package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.service.UserService;
import com.github.hesamjafari06.chat_server.serviceimpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<UserResponse> getProfile(@RequestParam UUID uid){
        return userService.getUserProfile(uid);

    }
}

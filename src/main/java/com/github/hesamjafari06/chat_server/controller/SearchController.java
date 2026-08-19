package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final UserService userservice;
    private final ChannelService channelService;

    @GetMapping("/user")
    public ApiResponse<List<UserResponse>> searchUser(@RequestParam String query){
        return userservice.searchUser(query);
    }

    @GetMapping("/channel")
    public ApiResponse<List<ChannelResponse>> searchChannel(@RequestParam String query){
        return channelService.searchChannel(query);
    }
}

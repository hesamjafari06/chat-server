package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.CreateChannelRequest;
import com.github.hesamjafari06.chat_server.dto.request.CreateGroupRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ChannelResponse;
import com.github.hesamjafari06.chat_server.dto.response.GroupResponse;
import com.github.hesamjafari06.chat_server.enums.CreateConversationType;
import com.github.hesamjafari06.chat_server.enums.SearchType;
import com.github.hesamjafari06.chat_server.service.ChannelService;
import com.github.hesamjafari06.chat_server.service.GroupService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ChannelService channelService;
    private final GroupService groupService;

    @GetMapping("/search")
    public ApiResponse<?> search(@RequestParam String query,
                                 @RequestParam SearchType type) {

        return switch (type) {
            case USER -> userService.searchUser(query);
            case CHANNEL -> channelService.searchChannel(query);
        };
    }

    @PostMapping("/create-channel")
    public ApiResponse<ChannelResponse> createChannel(@RequestBody CreateChannelRequest request){
        return channelService.createChannel(request);
    }

    @PostMapping("/create-group")
    public ApiResponse<GroupResponse> createGroup(@RequestBody CreateGroupRequest request){
        return groupService.createGroup(request);
    }

}

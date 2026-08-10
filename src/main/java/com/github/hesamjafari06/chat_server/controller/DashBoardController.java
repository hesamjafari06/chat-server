package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.ChangePasswordRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateUserRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.service.UserService;
import com.github.hesamjafari06.chat_server.serviceimpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final UserServiceImpl userService;

    @GetMapping
    public ApiResponse<UserResponse> getUserInfo(){
        return userService.getSelfUserProfile();
    }

    @PutMapping
    public ApiResponse<UserResponse> changeUserInfo(@RequestBody UpdateUserRequest request){
        return userService.updateUser(request);
    }

    @PostMapping("/change-password")
    public ApiResponse<UserResponse> changePassword(@RequestBody ChangePasswordRequest request){
        return userService.changePassword(request);
    }

}

package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.payload.request.ChangePasswordRequest;
import com.github.hesamjafari06.chat_server.payload.request.UpdateUserRequest;
import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.UpdateUserResponse;
import com.github.hesamjafari06.chat_server.payload.response.UserResponse;
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
    public ApiResponse<UpdateUserResponse> changeUserInfo(@RequestBody UpdateUserRequest request){
        return userService.updateUser(request);
    }

    @PostMapping("/change-password")
    public ApiResponse<UserResponse> changePassword(@RequestBody ChangePasswordRequest request){
        return userService.changePassword(request);
    }

}

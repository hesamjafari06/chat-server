package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UpdateUserResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiResponse<UserResponse> createUser(CreateUserRequest request);

    ApiResponse<UserResponse> getSelfUserProfile();

    ApiResponse<UserResponse> getUserProfile(String uid);

    ApiResponse<UpdateUserResponse> updateUser(UpdateUserRequest request);

    ApiResponse<UserResponse> changePassword(ChangePasswordRequest request);

    ApiResponse<List<UserResponse>> searchUser(String username);
}

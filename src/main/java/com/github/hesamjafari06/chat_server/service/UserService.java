package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UpdateUserResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    public ApiResponse<UserResponse> createUser(CreateUserRequest request);

    public UserEntity getCurrentUser();

    public UserEntity findUserById(Long id);

    public UserEntity findUserByUsername(String username);

    public UserEntity findUserByUserId(String userId);

    public ApiResponse<UserResponse> getSelfUserProfile();

    public ApiResponse<UserResponse> getUserProfile(String uid);

    public ApiResponse<UpdateUserResponse> updateUser(UpdateUserRequest request);

    public ApiResponse<UserResponse> changePassword(ChangePasswordRequest request);
}

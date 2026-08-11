package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UpdateUserResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    ApiResponse<UserResponse> createUser(CreateUserRequest request);

    UserEntity getCurrentUser();

    UserEntity findUserById(Long id);

    UserEntity findUserByUsername(String username);

    UserEntity findUserByUserId(String userId);

    ApiResponse<UserResponse> getSelfUserProfile();

    ApiResponse<UserResponse> getUserProfile(String uid);

    ApiResponse<UpdateUserResponse> updateUser(UpdateUserRequest request);

    ApiResponse<UserResponse> changePassword(ChangePasswordRequest request);
}

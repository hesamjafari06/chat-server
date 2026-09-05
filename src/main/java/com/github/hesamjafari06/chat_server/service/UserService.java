package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.payload.request.*;
import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.UpdateUserResponse;
import com.github.hesamjafari06.chat_server.payload.response.UserResponse;
import com.github.hesamjafari06.chat_server.payload.response.UserSearchResponse;

import java.util.List;

public interface UserService {
    ApiResponse<UserResponse> createUser(CreateUserRequest request);

    ApiResponse<UserResponse> getSelfUserProfile();

    ApiResponse<UserResponse> getUserProfile(String uid);

    ApiResponse<UpdateUserResponse> updateUser(UpdateUserRequest request);

    ApiResponse<UserResponse> changePassword(ChangePasswordRequest request);

    ApiResponse<List<UserSearchResponse>> searchUser(String username);
}

package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.CreateUserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

public interface UserService {
    public ApiResponse<CreateUserResponse> createUser(CreateUserRequest request);

    public UserEntity findUserById(Long id);

    public UserEntity findUserByUsername(String username);

    public CreateUserResponse loginUser(LoginRequest request);

    public CreateUserResponse updateUser(UpdateUserRequest request);

    public CreateUserResponse changePassword(ChangePasswordRequest request);
}

package com.github.hesamjafari06.chat_server.service;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;

public interface UserService {
    public UserResponse createUser(CreateUserRequest request);

    public UserEntity findUserById(Long id);

    public UserEntity findUserByUsername(String username);

    public UserResponse loginUser(LoginRequest request);

    public UserResponse updateUser(UpdateUserRequest request);

    public UserResponse changePassword(ChangePasswordRequest request);
}

package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.UserEntity;

public interface UserHelper {
    UserEntity getCurrentUser();

    UserEntity findUserByUsername(String username);

    UserEntity findUserByUserId(String userId);
}

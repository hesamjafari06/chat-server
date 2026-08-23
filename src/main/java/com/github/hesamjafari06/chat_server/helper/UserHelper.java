package com.github.hesamjafari06.chat_server.helper;

import com.github.hesamjafari06.chat_server.entity.UserEntity;

import java.util.List;

public interface UserHelper {
    UserEntity getCurrentUser();

    UserEntity findUserByUsername(String username);

    UserEntity findUserByUserId(String userId);

    boolean existsByUsername(String username);

    void save(UserEntity user);

    List<UserEntity> findByUsernameContainingIgnoreCase(String query);
}

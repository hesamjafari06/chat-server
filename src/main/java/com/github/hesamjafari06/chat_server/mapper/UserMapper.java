package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.request.CreateUserRequest;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserEntity toEntity(CreateUserRequest request){
        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .birthDate(request.getBirthDate())
                .build();
    }

    public UserResponse toUserResponse(UserEntity user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .build();
    }

    public UserDetails toUserDetails(UserEntity user){
        return new CustomUserDetails(user);
    }
}

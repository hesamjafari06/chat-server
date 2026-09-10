package com.github.hesamjafari06.chat_server.mapper;

import com.github.hesamjafari06.chat_server.dto.UserProfileDto;
import com.github.hesamjafari06.chat_server.dto.UserSearchDto;
import com.github.hesamjafari06.chat_server.payload.request.CreateUserRequest;
import com.github.hesamjafari06.chat_server.payload.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.payload.response.UserSearchResponse;
import com.github.hesamjafari06.chat_server.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserEntity toEntity(CreateUserRequest request){

        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .birthDate(LocalDate.of(request.getYear(), request.getMonth(), request.getDay()))
                .build();
    }

    public UserResponse toUserResponse(UserEntity user){

        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .year(user.getBirthDate().getYear())
                .month(user.getBirthDate().getMonthValue())
                .day(user.getBirthDate().getDayOfMonth())
                .createdAt(
                        user.getCreatedAt().toString().substring(0, 10)
                )
                .build();
    }

    public UserSearchResponse searchDtoToResponse(UserSearchDto user){

        return UserSearchResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();
    }

    public UserResponse profileDtoToResponse(UserProfileDto user){

        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .year(user.getBirthDate().getYear())
                .month(user.getBirthDate().getMonthValue())
                .day(user.getBirthDate().getDayOfMonth())
                .createdAt(
                        user.getCreatedAt().toString().substring(0, 10)
                )
                .build();
    }

    public UserDetails toUserDetails(UserEntity user){
        return new CustomUserDetails(user);
    }
}
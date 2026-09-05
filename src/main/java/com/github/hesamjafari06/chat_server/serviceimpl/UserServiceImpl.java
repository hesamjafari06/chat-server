package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.payload.request.*;
import com.github.hesamjafari06.chat_server.payload.response.ApiResponse;
import com.github.hesamjafari06.chat_server.payload.response.UpdateUserResponse;
import com.github.hesamjafari06.chat_server.payload.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.exception.UsernameAlreadyExistsException;
import com.github.hesamjafari06.chat_server.exception.WrongPasswordException;
import com.github.hesamjafari06.chat_server.helper.UserHelper;
import com.github.hesamjafari06.chat_server.mapper.UserMapper;
import com.github.hesamjafari06.chat_server.payload.response.UserSearchResponse;
import com.github.hesamjafari06.chat_server.security.JwtService;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    @Transactional
    public ApiResponse<UserResponse> createUser(CreateUserRequest request) {

        if (userHelper.existsByUsername(request.getUsername())) {

            throw new UsernameAlreadyExistsException();
        }

        UserEntity user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userHelper.save(user);

        return ApiResponse.<UserResponse>builder()
                .status("OK")
                .data(userMapper.toUserResponse(user))
                .build();
    }


    @Override
    public ApiResponse<UserResponse> getSelfUserProfile() {

        UserEntity user = userHelper.getCurrentUser();

        return ApiResponse.<UserResponse>builder()
                .status("OK")
                .data(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    public ApiResponse<UserResponse> getUserProfile(String uid) {

        return ApiResponse.<UserResponse>builder()
                .status("OK")
                .data(userMapper.profileDtoToResponse(userHelper.findUserProfileByUserId(uid)))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UpdateUserResponse> updateUser(UpdateUserRequest request) {

        UserEntity user = userHelper.getCurrentUser();

        if (request.getUsername() != null && !Objects.equals(user.getUsername(), request.getUsername())) {

            if (userHelper.existsByUsername(request.getUsername())) {

                throw new UsernameAlreadyExistsException();
            }

            user.setUsername(request.getUsername());
        }

        if (request.getBirthDate() != null && !Objects.equals(user.getBirthDate(), request.getBirthDate())) {

            user.setBirthDate(request.getBirthDate());
        }


        return ApiResponse.<UpdateUserResponse>builder()
                .status("OK")
                .data(UpdateUserResponse.builder()
                        .userResponse(userMapper.toUserResponse(user))
                        .token(jwtService.generateToken(userDetailsService.loadUserByUsername(user.getUsername())))
                        .build())
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> changePassword(ChangePasswordRequest request) {

//        UserEntity user = getCurrentUser();
        UserEntity user = userHelper.getCurrentUser();

        if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            return ApiResponse.<UserResponse>builder()
                    .status("OK")
                    .data(userMapper.toUserResponse(user))
                    .build();
        }

        throw new WrongPasswordException();
    }

    @Override
    public ApiResponse<List<UserSearchResponse>> searchUser(String username){

        List<UserSearchResponse> users =
                userHelper.findByUsernameContainingIgnoreCase(username)
                        .stream()
                        .map(userMapper::searchDtoToResponse)
                        .toList();

        return ApiResponse.<List<UserSearchResponse>>builder()
                .status("OK")
                .data(users)
                .build();
    }
}

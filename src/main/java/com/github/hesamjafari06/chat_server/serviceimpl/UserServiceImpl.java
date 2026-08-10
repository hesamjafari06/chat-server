package com.github.hesamjafari06.chat_server.serviceimpl;

import com.github.hesamjafari06.chat_server.dto.request.*;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.exception.PasswordDoNotMatchException;
import com.github.hesamjafari06.chat_server.exception.UserNotFoundException;
import com.github.hesamjafari06.chat_server.exception.UsernameAlreadyExistsException;
import com.github.hesamjafari06.chat_server.exception.WrongPasswordException;
import com.github.hesamjafari06.chat_server.mapper.UserMapper;
import com.github.hesamjafari06.chat_server.repository.UserRepository;
import com.github.hesamjafari06.chat_server.security.CustomUserDetails;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserEntity getCurrentUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUser().getId();

        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }


    @Override
    @Transactional
    public ApiResponse<UserResponse> createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())){
            throw new UsernameAlreadyExistsException();
        }

        UserEntity user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ApiResponse.<UserResponse>builder()
                .status("OK")
                .data(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ApiResponse<UserResponse> getSelfUserProfile(){
        UserEntity user = getCurrentUser();

        return ApiResponse.<UserResponse>builder()
                .status("OK")
                .data(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> updateUser(UpdateUserRequest request) {

        UserEntity user = getCurrentUser();

        if (request.getUsername() != null &&
                !Objects.equals(user.getUsername(), request.getUsername())) {

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new UsernameAlreadyExistsException();
            }

            user.setUsername(request.getUsername());
        }

        if (request.getBirthDate() != null &&
                !Objects.equals(user.getBirthDate(), request.getBirthDate())) {

            user.setBirthDate(request.getBirthDate());
        }

        return ApiResponse.<UserResponse>builder()
                .status("OK")
                .data(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> changePassword(ChangePasswordRequest request) {
        UserEntity user = getCurrentUser();
        if (
                passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())
        ) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            return ApiResponse.<UserResponse>builder()
                    .status("OK")
                    .data(userMapper.toUserResponse(user))
                    .build();
        }
        throw new WrongPasswordException();
    }

}

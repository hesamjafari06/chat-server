package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.CreateUserRequest;
import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.UserResponse;
import com.github.hesamjafari06.chat_server.exception.AlreadyAuthenticatedException;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<Void> checkLogin(){

        Authentication currentAuthentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (currentAuthentication != null
                && currentAuthentication.isAuthenticated()
                && !(currentAuthentication instanceof AnonymousAuthenticationToken)) {

            throw new AlreadyAuthenticatedException();
        }

        return ApiResponse.<Void>builder()
                .status("OK")
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> register(
            @RequestBody CreateUserRequest request
    ) {

        return userService.createUser(request);
    }
}
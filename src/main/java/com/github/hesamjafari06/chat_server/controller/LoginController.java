package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.LoginRequest;
import com.github.hesamjafari06.chat_server.dto.response.LoginResponse;
import com.github.hesamjafari06.chat_server.exception.InvalidLoginException;
import com.github.hesamjafari06.chat_server.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );
            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());

            return new LoginResponse(token);
        } catch (BadCredentialsException exception) {
            throw new InvalidLoginException();
        }
    }
}

package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}

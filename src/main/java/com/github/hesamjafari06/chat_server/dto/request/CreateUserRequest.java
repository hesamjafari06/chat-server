package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateUserRequest {
    private String username;
    private String password;
    private LocalDate birthDate;
}

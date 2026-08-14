package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateUserRequest {
    private String username;
    private LocalDate birthDate;
}

package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String password;
    private LocalDate birthDate;
}

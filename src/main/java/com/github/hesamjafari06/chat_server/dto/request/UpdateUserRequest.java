package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserRequest {
    private String username;
    private LocalDate birthDate;
}

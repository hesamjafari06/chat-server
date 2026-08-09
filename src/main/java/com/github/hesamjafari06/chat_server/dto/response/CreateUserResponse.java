package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateUserResponse {
    private String username;
    private LocalDate birthDate;
}

package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Builder
public class UserResponse {
    private String userId;
    private String username;
    private LocalDate birthDate;
}

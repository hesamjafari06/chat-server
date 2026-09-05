package com.github.hesamjafari06.chat_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileDto {
    private String userId;
    private String username;
    private LocalDate birthDate;
    private Instant createdAt;
}

package com.github.hesamjafari06.chat_server.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class UserResponse {
    private String userId;
    private String username;
    private int year;
    private int month;
    private int day;
    private String createdAt;
}

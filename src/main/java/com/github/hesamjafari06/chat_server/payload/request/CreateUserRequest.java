package com.github.hesamjafari06.chat_server.payload.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateUserRequest {
    private String username;
    private String password;
    private int year;
    private int month;
    private int day;
}

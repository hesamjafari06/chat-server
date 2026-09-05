package com.github.hesamjafari06.chat_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSearchDto {
    private String userId;
    private String username;
}

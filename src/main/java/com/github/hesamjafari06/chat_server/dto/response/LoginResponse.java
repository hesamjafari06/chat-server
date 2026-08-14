package com.github.hesamjafari06.chat_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Builder
public class LoginResponse {
    private String token;
}

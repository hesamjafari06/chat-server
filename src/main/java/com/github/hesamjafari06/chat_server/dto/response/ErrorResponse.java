package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Setter;


@Setter
@Builder
public class ErrorResponse {
    private String message;
}

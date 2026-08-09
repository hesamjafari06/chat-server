package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private String status;
    private T data;
    private Instant timestamp;
}

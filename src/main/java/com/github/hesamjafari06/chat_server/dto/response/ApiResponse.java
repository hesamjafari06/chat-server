package com.github.hesamjafari06.chat_server.dto.response;

import lombok.Builder;
import lombok.Setter;

import java.time.Instant;

@Setter
@Builder
public class ApiResponse<T> {

    private String status;
    private T data;
    @Builder.Default
    private Instant timestamp = Instant.now();

}

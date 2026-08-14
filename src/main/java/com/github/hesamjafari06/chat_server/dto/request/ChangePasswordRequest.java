package com.github.hesamjafari06.chat_server.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}

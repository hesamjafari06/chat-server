package com.github.hesamjafari06.chat_server.dto.response;

import com.github.hesamjafari06.chat_server.enums.ConversationType;
import lombok.Builder;
import lombok.Setter;

import java.time.Instant;

@Setter
@Builder
public class MessageResponse {
    private String messageId;
    private String senderId;
    private String senderUsername;
    private String content;
    private String conversationId;
    private Instant sendAt;
    private String replyTo;
    private String replyContent;
    private ConversationType type;
    private boolean isEdited;
    private Instant editedAt;
}

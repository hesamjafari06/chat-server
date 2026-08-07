package com.github.hesamjafari06.chat_server.exception;

public class ConversationNotFoundException extends RuntimeException{
    public ConversationNotFoundException(){
        super("Conversation not found");
    }
}

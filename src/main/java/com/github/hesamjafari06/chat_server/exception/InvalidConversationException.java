package com.github.hesamjafari06.chat_server.exception;

public class InvalidConversationException extends RuntimeException{
    public InvalidConversationException(){
        super("Invalid Conversation request");
    }
}

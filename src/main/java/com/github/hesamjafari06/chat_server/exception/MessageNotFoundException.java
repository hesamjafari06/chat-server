package com.github.hesamjafari06.chat_server.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(){
        super("message not found");
    }
}

package com.github.hesamjafari06.chat_server.exception;

public class JoinPrivateConversationException extends RuntimeException{
    public JoinPrivateConversationException(){
        super("Can't join private conversation");
    }
}

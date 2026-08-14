package com.github.hesamjafari06.chat_server.exception;

public class NotMemberMessageException extends RuntimeException{
    public NotMemberMessageException(){
        super("can't update other member message");
    }
}

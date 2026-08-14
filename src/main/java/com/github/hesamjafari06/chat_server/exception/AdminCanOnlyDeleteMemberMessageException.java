package com.github.hesamjafari06.chat_server.exception;

public class AdminCanOnlyDeleteMemberMessageException extends RuntimeException{
    public AdminCanOnlyDeleteMemberMessageException(){
        super("Admin can only delete member's messages");
    }
}

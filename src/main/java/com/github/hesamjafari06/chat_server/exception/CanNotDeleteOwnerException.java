package com.github.hesamjafari06.chat_server.exception;

public class CanNotDeleteOwnerException extends RuntimeException {
    public CanNotDeleteOwnerException(){
        super("Owner can't get deleted");
    }
}

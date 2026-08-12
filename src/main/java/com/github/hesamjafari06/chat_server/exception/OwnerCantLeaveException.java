package com.github.hesamjafari06.chat_server.exception;

public class OwnerCantLeaveException extends RuntimeException{
    public OwnerCantLeaveException(){
        super("Owner can't leave");
    }
}

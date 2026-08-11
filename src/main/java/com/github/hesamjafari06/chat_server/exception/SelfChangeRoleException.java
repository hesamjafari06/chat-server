package com.github.hesamjafari06.chat_server.exception;

public class SelfChangeRoleException extends RuntimeException{
    public SelfChangeRoleException(){
        super("Can't change self role ");
    }
}

package com.github.hesamjafari06.chat_server.exception;

public class NoOwnerChangeRoleException extends RuntimeException{
    public NoOwnerChangeRoleException(){
        super("Members can't change roles");
    }
}

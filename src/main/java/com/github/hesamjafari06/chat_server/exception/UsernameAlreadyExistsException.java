package com.github.hesamjafari06.chat_server.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){
        super("username already exists");
    }
}

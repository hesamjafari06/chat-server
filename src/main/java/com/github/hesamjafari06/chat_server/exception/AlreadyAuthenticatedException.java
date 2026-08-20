package com.github.hesamjafari06.chat_server.exception;

public class AlreadyAuthenticatedException extends RuntimeException{
    public AlreadyAuthenticatedException(){
        super("User is Already Authenticated");
    }
}

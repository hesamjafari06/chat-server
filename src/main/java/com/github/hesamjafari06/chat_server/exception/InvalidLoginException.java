package com.github.hesamjafari06.chat_server.exception;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(){
        super("Username or Password was wrong");
    }
}

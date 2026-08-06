package com.github.hesamjafari06.chat_server.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(){
        super("Password was wrong");
    }
}

package com.github.hesamjafari06.chat_server.exception;

public class PasswordDoNotMatchException extends RuntimeException{
    public PasswordDoNotMatchException(){
        super("new password's confirmation is not correct");
    }
}

package com.github.hesamjafari06.chat_server.exception;

public class PublicIdAlreadyExistsException extends RuntimeException{
    public PublicIdAlreadyExistsException(){
        super("PublicId already taken");
    }
}

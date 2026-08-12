package com.github.hesamjafari06.chat_server.exception;

public class OnlyOwnerChangeGroupException extends RuntimeException{
    public OnlyOwnerChangeGroupException(){
        super("Only owner can update group");
    }
}

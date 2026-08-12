package com.github.hesamjafari06.chat_server.exception;

public class OnlyOwnerCanDeleteException extends RuntimeException{
    public OnlyOwnerCanDeleteException(){
        super("Only Owner can delete");
    }
}

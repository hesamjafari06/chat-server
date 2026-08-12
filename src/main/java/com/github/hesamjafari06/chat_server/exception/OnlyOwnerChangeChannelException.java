package com.github.hesamjafari06.chat_server.exception;

public class OnlyOwnerChangeChannelException extends RuntimeException{
    public OnlyOwnerChangeChannelException(){
        super("Only Owner can update channel");
    }
}

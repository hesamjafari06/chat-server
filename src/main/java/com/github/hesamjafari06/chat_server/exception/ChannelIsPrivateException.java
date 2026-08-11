package com.github.hesamjafari06.chat_server.exception;

public class ChannelIsPrivateException extends RuntimeException{
    public ChannelIsPrivateException(){
        super("Channel is private");
    }
}

package com.github.hesamjafari06.chat_server.exception;

public class ChannelNotFoundException extends RuntimeException{
    public ChannelNotFoundException(){
        super("Channel not found");
    }
}

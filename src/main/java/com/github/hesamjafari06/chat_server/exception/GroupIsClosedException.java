package com.github.hesamjafari06.chat_server.exception;

public class GroupIsClosedException extends RuntimeException{
    public GroupIsClosedException(){
        super("Group is closed");
    }
}

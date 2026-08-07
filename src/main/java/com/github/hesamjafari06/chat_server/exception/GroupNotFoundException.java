package com.github.hesamjafari06.chat_server.exception;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(){
        super("Group not found");
    }
}

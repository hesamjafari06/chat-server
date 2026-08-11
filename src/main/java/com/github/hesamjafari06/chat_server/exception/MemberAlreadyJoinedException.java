package com.github.hesamjafari06.chat_server.exception;

public class MemberAlreadyJoinedException extends RuntimeException{
    public MemberAlreadyJoinedException(){
        super("Member already joined");
    }
}

package com.github.hesamjafari06.chat_server.exception;

public class MemberIsNotJoinedException extends RuntimeException{
    public MemberIsNotJoinedException(){
        super("Member is not in conversation");
    }
}

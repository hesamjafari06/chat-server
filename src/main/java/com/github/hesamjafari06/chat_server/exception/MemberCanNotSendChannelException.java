package com.github.hesamjafari06.chat_server.exception;

public class MemberCanNotSendChannelException extends RuntimeException{
    public MemberCanNotSendChannelException(){
        super("Members can't send message in channels");
    }
}

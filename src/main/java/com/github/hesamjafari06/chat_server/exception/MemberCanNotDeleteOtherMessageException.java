package com.github.hesamjafari06.chat_server.exception;

public class MemberCanNotDeleteOtherMessageException extends RuntimeException{
    public MemberCanNotDeleteOtherMessageException(){
        super("Members can't delete other member's messages");
    }
}

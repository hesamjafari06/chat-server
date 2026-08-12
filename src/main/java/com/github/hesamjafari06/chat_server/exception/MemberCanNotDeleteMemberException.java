package com.github.hesamjafari06.chat_server.exception;

public class MemberCanNotDeleteMemberException extends RuntimeException{
    public MemberCanNotDeleteMemberException(){
        super("Members can't delete other members");
    }
}

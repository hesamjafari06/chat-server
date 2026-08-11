package com.github.hesamjafari06.chat_server.exception;

public class ConversationMemberNotFoundException extends RuntimeException{
    public ConversationMemberNotFoundException(){
        super("ConversationMember not found");
    }
}

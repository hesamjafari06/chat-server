package com.github.hesamjafari06.chat_server.exception;

public class ReplyOtherConversationException extends RuntimeException{
    public ReplyOtherConversationException(){
        super("Can't reply to messages from other conversations");
    }
}

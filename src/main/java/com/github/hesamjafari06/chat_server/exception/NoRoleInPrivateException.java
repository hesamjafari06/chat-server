package com.github.hesamjafari06.chat_server.exception;

public class NoRoleInPrivateException extends RuntimeException{
    public NoRoleInPrivateException(){
        super("There is no role in private chat");
    }
}

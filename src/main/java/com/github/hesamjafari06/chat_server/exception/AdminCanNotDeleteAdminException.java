package com.github.hesamjafari06.chat_server.exception;

public class AdminCanNotDeleteAdminException extends RuntimeException{
    public AdminCanNotDeleteAdminException(){
        super("Admin can't delete other admins");
    }
}

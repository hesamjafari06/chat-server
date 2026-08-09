package com.github.hesamjafari06.chat_server.exception;

import com.github.hesamjafari06.chat_server.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(String message){
        return ErrorResponse.builder()
                .message(message)
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserUsernameAlreadyExists(
            UsernameAlreadyExistsException exception){

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException exception){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPassword(
            WrongPasswordException exception){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(ConversationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConversationNotFound(
            ConversationNotFoundException exception){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotFound(
            GroupNotFoundException exception){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(PublicIdAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePublicIdAlreadyExists(
            PublicIdAlreadyExistsException exception){

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleChannelNotFound(
            ChannelNotFoundException exception){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> handleChannelNotFound(
            InvalidLoginException exception){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        buildErrorResponse(exception.getMessage())
                );
    }
}

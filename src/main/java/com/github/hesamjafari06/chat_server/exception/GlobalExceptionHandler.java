package com.github.hesamjafari06.chat_server.exception;

import com.github.hesamjafari06.chat_server.dto.response.ApiResponse;
import com.github.hesamjafari06.chat_server.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(String message){
        return ErrorResponse.builder()
                .message(message)
                .build();
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<ErrorResponse> handleUserUsernameAlreadyExists(
            UsernameAlreadyExistsException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("CONFLICT")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorResponse> handleUserNotFound(
            UserNotFoundException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("NOT_FOUND")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ApiResponse<ErrorResponse> handleWrongPassword(
            WrongPasswordException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleInvalidLogin(
            InvalidLoginException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(InvalidConversationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleInvalidConversation(
            InvalidConversationException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(JoinPrivateConversationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleJoinPrivateConversation(
            JoinPrivateConversationException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(ChannelIsPrivateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleChannelPrivate(
            ChannelIsPrivateException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(GroupIsClosedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleGroupIsClosed(
            GroupIsClosedException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MemberAlreadyJoinedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberAlreadyJoined(
            MemberAlreadyJoinedException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(NoRoleInPrivateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleNoRoleInPrivate(
            NoRoleInPrivateException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(NoOwnerChangeRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberCantChangeRole(
            NoOwnerChangeRoleException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MemberIsNotJoinedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberIsNotJoined(
            MemberIsNotJoinedException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(SelfChangeRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleSelfChangeRole(
            SelfChangeRoleException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(OwnerCantLeaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleOwnerCantLeave(
            OwnerCantLeaveException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(OnlyOwnerCanDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleOnlyOwnerCanDelete(
            OnlyOwnerCanDeleteException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(OnlyOwnerChangeGroupException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberChangeGroup(
            OnlyOwnerChangeGroupException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MemberCanNotDeleteMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberCanNotDeleteMember(
            MemberCanNotDeleteMemberException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(CanNotDeleteOwnerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleCanNotDeleteOwner(
            CanNotDeleteOwnerException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(AdminCanNotDeleteAdminException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleAdminCanNotDeleteAdmin(
            AdminCanNotDeleteAdminException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorResponse> handleMessageNotFound(
            MessageNotFoundException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("NOT_FOUND")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MemberCanNotSendChannelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberCanNotSendChannel(
            MemberCanNotSendChannelException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(ReplyOtherConversationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleReplyOtherConversation(
            ReplyOtherConversationException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(NotMemberMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleNotMemberMessage(
            NotMemberMessageException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MemberCanNotDeleteOtherMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleMemberCanNotDeleteMemberMessage(
            MemberCanNotDeleteOtherMessageException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(AdminCanOnlyDeleteMemberMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleAdminCanOnlyDeleteMemberMessage(
            AdminCanOnlyDeleteMemberMessageException exception){

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse("Invalid search type"))
                .build();
    }

    @ExceptionHandler(AlreadyAuthenticatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleTypeMismatch(
            AlreadyAuthenticatedException exception) {

        return ApiResponse.<ErrorResponse>builder()
                .status("BAD_REQUEST")
                .data(buildErrorResponse(exception.getMessage()))
                .build();
    }
}


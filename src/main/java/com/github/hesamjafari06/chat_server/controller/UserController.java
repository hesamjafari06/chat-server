package com.github.hesamjafari06.chat_server.controller;

import com.github.hesamjafari06.chat_server.dto.request.ChangePasswordRequest;
import com.github.hesamjafari06.chat_server.dto.request.UpdateUserRequest;
import com.github.hesamjafari06.chat_server.dto.response.CreateUserResponse;
import com.github.hesamjafari06.chat_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<UserResponse> getUserInfo(){
//        UserResponse response = userService.updateUser(request);
//
//        return ResponseEntity.ok(response);
//    }

    @PatchMapping()
    public ResponseEntity<CreateUserResponse> updateUser(@RequestBody UpdateUserRequest request){
        CreateUserResponse response = userService.updateUser(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request){

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        CreateUserResponse response = userService.changePassword(request);

        return ResponseEntity.ok().build();
    }
}

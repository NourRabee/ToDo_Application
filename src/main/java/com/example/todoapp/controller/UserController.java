package com.example.todoapp.controller;

import com.example.todoapp.domain.dto.SignInRequest;
import com.example.todoapp.domain.dto.SignInResponse;
import com.example.todoapp.domain.dto.SignUpRequest;
import com.example.todoapp.domain.dto.SignUpResponse;
import com.example.todoapp.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse response = userService.register(signUpRequest);

        if(response == null){
            throw new IllegalArgumentException("User already exists.");
        }else{
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> SignIn(@RequestBody SignInRequest signInRequest){
        SignInResponse response = userService.signIn(signInRequest);

        if(response == null){
            throw new IllegalArgumentException("Email or Password is incorrect!");
        }else{
            return ResponseEntity.ok(response);
        }

    }
}

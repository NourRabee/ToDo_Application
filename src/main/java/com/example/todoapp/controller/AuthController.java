package com.example.todoapp.controller;

import com.example.todoapp.domain.dto.*;
import com.example.todoapp.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse response = userService.register(signUpRequest);

        if(response == null){
            throw new IllegalArgumentException("User already exists.");
        }else{
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> SignIn(@Valid @RequestBody SignInRequest signInRequest){
        SignInResponse response = userService.signIn(signInRequest);

        if(response == null){
            throw new IllegalArgumentException("Email or Password is incorrect!");
        }else{
            return ResponseEntity.ok(response);
        }

    }
    @GetMapping("/users")
    public List<UserResponse> getUsers(){

        return userService.getUsers();

    }
}

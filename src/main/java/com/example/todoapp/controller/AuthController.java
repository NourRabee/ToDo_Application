package com.example.todoapp.controller;

import com.example.todoapp.domain.dto.*;
import com.example.todoapp.service.interfaces.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        SignUpResponse response = authService.register(signUpRequest);

        if(response == null){
            throw new IllegalArgumentException("User already exists.");
        }else{
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> SignIn(@Valid @RequestBody SignInRequest signInRequest){

        SignInResponse response = authService.signIn(signInRequest);

        if(response == null){
            throw new IllegalArgumentException("Email or Password is incorrect!");
        }else{
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/password-reset-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody PasswordResetRequest request) throws MessagingException {
        authService.request_password_reset(request);

        return ResponseEntity.ok("A verification code has been sent to your email. Please check your inbox to continue.");
    }

    @PostMapping("/verify-password-reset-token")
    public ResponseEntity<String> verifyPasswordResetToken(@RequestBody VerifyPasswordResetTokenRequest request){

        boolean verified = authService.verifyPasswordResetToken(request);

        if(verified){
            return ResponseEntity.ok("The verification code is valid. You may now reset your password.");
        }
        return ResponseEntity.badRequest().body("The verification code is invalid or has expired. Please request a new one.");

    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordReset request) throws MessagingException {

        boolean isResetSuccessful = authService.resetPassword(request);

        if (isResetSuccessful) {
            return ResponseEntity.ok("Your password has been successfully reset. You can now log in with your new password.");
        }
        return ResponseEntity.badRequest().body("This password may have been used before. Try a new one.");
    }


}

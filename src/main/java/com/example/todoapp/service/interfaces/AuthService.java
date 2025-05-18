package com.example.todoapp.service.interfaces;

import com.example.todoapp.domain.dto.*;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    SignUpResponse register(SignUpRequest signUpRequest);
    SignInResponse signIn(SignInRequest signInRequest);

    void request_password_reset(PasswordResetRequest request) throws MessagingException;

    boolean verifyPasswordResetToken(VerifyPasswordResetTokenRequest request);

    boolean resetPassword(PasswordReset request);
}

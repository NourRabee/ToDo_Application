package com.example.todoapp.service.interfaces;

import com.example.todoapp.domain.dto.SignInRequest;
import com.example.todoapp.domain.dto.SignInResponse;
import com.example.todoapp.domain.dto.SignUpRequest;
import com.example.todoapp.domain.dto.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    SignUpResponse register(SignUpRequest signUpRequest);
    SignInResponse signIn(SignInRequest signInRequest);
}

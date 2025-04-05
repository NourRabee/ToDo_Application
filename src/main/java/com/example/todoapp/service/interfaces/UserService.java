package com.example.todoapp.service.interfaces;

import com.example.todoapp.domain.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    SignUpResponse register(SignUpRequest signUpRequest);
    SignInResponse signIn(SignInRequest signInRequest);

    List<UserResponse> getUsers();
}

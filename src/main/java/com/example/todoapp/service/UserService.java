package com.example.todoapp.service;

import com.example.todoapp.domain.dto.SignInRequest;
import com.example.todoapp.domain.dto.SignInResponse;
import com.example.todoapp.domain.dto.SignUpRequest;
import com.example.todoapp.domain.dto.SignUpResponse;
import com.example.todoapp.domain.mapper.UserMapper;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    public SignUpResponse register(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()) != null) {

            return null;
        }

        User user = userMapper.signUpRequestToUser(signUpRequest);

        userRepository.save(user);

        return userMapper.userToSignUpResponse(user);
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail((signInRequest.getEmail()));
        if(user == null || !passwordService.validatePassword(signInRequest.getPassword(), user.getHashedPassword())) {
            return null;
        }
        return new SignInResponse(user.getId());
    }
}
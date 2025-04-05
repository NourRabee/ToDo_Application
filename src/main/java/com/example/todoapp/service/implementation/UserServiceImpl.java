package com.example.todoapp.service.implementation;

import com.example.todoapp.domain.dto.*;
import com.example.todoapp.domain.mapper.UserMapper;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.service.interfaces.PasswordService;
import com.example.todoapp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final PasswordService passwordService;
    @Autowired
    private final JWTService jwtService; // Spring automatically creates an instance of JWTService and injects it where you need it.

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
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
        String token = jwtService.generateToken(user.getFullName());
        return new SignInResponse(token);
    }

    public List<UserResponse> getUsers() {

       List<User> users = userRepository.findAll();

       return userMapper.usersToUserResponses(users);


    }

}
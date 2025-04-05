package com.example.todoapp.domain.mapper;

import com.example.todoapp.domain.dto.SignUpRequest;
import com.example.todoapp.domain.dto.SignUpResponse;
import com.example.todoapp.domain.dto.UserResponse;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.service.implementation.PasswordServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Autowired
    PasswordServiceImpl passwordService = new PasswordServiceImpl();
 
    @Mapping(target = "hashedPassword", expression = "java(passwordService.hashPassword(request.getPassword()))")
    abstract User signUpRequestToUser(SignUpRequest request);

    abstract SignUpResponse userToSignUpResponse(User user);

    abstract UserResponse userToUserResponse(User user);

    abstract List<UserResponse> usersToUserResponses(List<User> users);

}

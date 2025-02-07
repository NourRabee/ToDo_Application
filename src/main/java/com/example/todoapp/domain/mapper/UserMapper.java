package com.example.todoapp.domain.mapper;

import com.example.todoapp.domain.dto.SignUpRequest;
import com.example.todoapp.domain.dto.SignUpResponse;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.service.PasswordService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Autowired
    PasswordService passwordService = new PasswordService();
 
    @Mapping(target = "hashedPassword", expression = "java(passwordService.hashPassword(request.getPassword()))")
    abstract User signUpRequestToUser(SignUpRequest request);

    abstract SignUpResponse userToSignUpResponse(User user);

}

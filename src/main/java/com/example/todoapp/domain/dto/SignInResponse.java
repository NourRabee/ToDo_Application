package com.example.todoapp.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {

    private String JWTToken;

    public SignInResponse(String JWTToken) {
        this.JWTToken = JWTToken;
    }
}
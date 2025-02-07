package com.example.todoapp.domain.dto;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String email;
    private String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

package com.example.todoapp.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {

    private int id;

    public SignInResponse(int id) {
        this.id = id;
    }
}
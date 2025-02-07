package com.example.todoapp.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponse {
    private int id;

    public SignUpResponse(int id) {
        this.id = id;
    }
}

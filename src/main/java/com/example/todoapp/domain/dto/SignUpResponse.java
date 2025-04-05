package com.example.todoapp.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpResponse {
    private int id;

    public SignUpResponse(int id) {
        this.id = id;
    }
}

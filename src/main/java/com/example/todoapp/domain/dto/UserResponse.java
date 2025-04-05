package com.example.todoapp.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    String fullName;
    String email;

    public UserResponse(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}

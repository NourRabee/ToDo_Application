package com.example.todoapp.service.interfaces;
import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    String hashPassword(String password);
    Boolean validatePassword(String password, String hashedPassword);
}

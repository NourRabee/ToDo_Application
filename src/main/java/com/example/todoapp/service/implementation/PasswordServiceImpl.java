package com.example.todoapp.service.implementation;
import com.example.todoapp.service.interfaces.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public Boolean validatePassword(String password, String hashedPassword){
        return passwordEncoder.matches(password, hashedPassword);
    }
}

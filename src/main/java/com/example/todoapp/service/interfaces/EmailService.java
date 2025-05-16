package com.example.todoapp.service.interfaces;

import com.example.todoapp.domain.model.PasswordResetToken;
import com.example.todoapp.domain.model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService{

    String loadEmailTemplate(String s);
    void sendEmail(String htmlBody, User user) throws MessagingException;
}

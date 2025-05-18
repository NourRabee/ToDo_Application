package com.example.todoapp.service.interfaces;

import com.example.todoapp.EmailTemplates;
import com.example.todoapp.domain.model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService{

    String loadEmailTemplate(EmailTemplates template, User user);
    void sendEmail(String htmlBody, String subject, User user) throws MessagingException;
}

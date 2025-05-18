package com.example.todoapp.service.implementation;

import com.example.todoapp.EmailTemplates;
import com.example.todoapp.domain.model.PasswordResetToken;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.todoapp.EmailTemplates.RESET_PASSWORD_TEMPLATE;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    private String loadResetPasswordTemplate(String filename,PasswordResetToken token, User user) {

        try {
            ClassPathResource resource = new ClassPathResource("templates/email/" + filename);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{ full_name }}", user.getFullName())
                    .replace("{{ reset_code }}", token.getToken())
                    .replace("{{ project_name }}", "Nour's Todo Application")
                    .replace("{{ year }}", "2025");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }

    public String loadEmailTemplate(EmailTemplates template, PasswordResetToken token, User user) {
        return switch (template) {
            case RESET_PASSWORD_TEMPLATE -> loadResetPasswordTemplate(template.getFileName(), token, user);
        };
    }

    @Override
    public void sendEmail(String htmlBody, User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject("Your Access Code");
        helper.setText(htmlBody, true);
        mailSender.send(message);

    }

}
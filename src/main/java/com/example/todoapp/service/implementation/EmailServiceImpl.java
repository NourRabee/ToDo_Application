package com.example.todoapp.service.implementation;

import com.example.todoapp.EmailTemplates;
import com.example.todoapp.domain.model.PasswordResetToken;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.repository.PasswordResetTokenRepository;
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

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private String loadVerificationCodeTemplate(String filename, PasswordResetToken token, User user) {

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

    private String loadPasswordChangeConfirmationTemplate(String filename, User user){

        try {
            ClassPathResource resource = new ClassPathResource("templates/email/" + filename);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{ full_name }}", user.getFullName());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }

    public String loadEmailTemplate(EmailTemplates template, User user) {
        return switch (template) {
            case VERIFICATION_CODE_TEMPLATE -> {
                PasswordResetToken token = passwordResetTokenRepository.findTopByUserOrderByCreatedAtDesc(user);
                yield loadVerificationCodeTemplate(template.getFileName(), token, user);
            }
            case PASSWORD_CHANGE_CONFIRMATION_TEMPLATE ->
                    loadPasswordChangeConfirmationTemplate(template.getFileName(), user);

        };
    }

    @Override
    public void sendEmail(String htmlBody, String subject, User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);

    }

}
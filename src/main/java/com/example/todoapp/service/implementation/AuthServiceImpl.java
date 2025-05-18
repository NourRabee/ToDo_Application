package com.example.todoapp.service.implementation;

import com.example.todoapp.domain.dto.*;
import com.example.todoapp.domain.mapper.UserMapper;
import com.example.todoapp.domain.model.PasswordResetToken;
import com.example.todoapp.domain.model.User;
import com.example.todoapp.repository.PasswordResetTokenRepository;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.service.interfaces.EmailService;
import com.example.todoapp.service.interfaces.PasswordService;
import com.example.todoapp.service.interfaces.AuthService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.example.todoapp.EmailTemplates.PASSWORD_CHANGE_CONFIRMATION_TEMPLATE;
import static com.example.todoapp.EmailTemplates.VERIFICATION_CODE_TEMPLATE;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;
    private final EmailService emailService;
    private final JWTService jwtService; // Spring automatically creates an instance of JWTService and injects it where you need it.
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService,
                           JWTService jwtService, EmailService emailService, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public SignUpResponse register(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()) != null) {

            return null;
        }

        User user = userMapper.signUpRequestToUser(signUpRequest);

        userRepository.save(user);

        return userMapper.userToSignUpResponse(user);
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail((signInRequest.getEmail()));
        if(user == null || !passwordService.validatePassword(signInRequest.getPassword(), user.getHashedPassword())) {
            return null;
        }
        String token = jwtService.generateToken(user.getEmail());
        return new SignInResponse(token);
    }

    public PasswordResetToken createToken(User user){

        String tokenValue = UUID.randomUUID().toString();
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(tokenValue);
        token.setIsUsed(false);
        token.setUser(user);
        passwordResetTokenRepository.save(token);
        return token;

    }

    @Override
    public void request_password_reset(PasswordResetRequest request) throws MessagingException {
        User user = userRepository.findByEmail(request.getEmail());
        if(user != null){

            createToken(user);

            String htmlBody = emailService.loadEmailTemplate(VERIFICATION_CODE_TEMPLATE, user);
            emailService.sendEmail(htmlBody, "Your Access Code" ,user);
        }
    }

    @Override
    public boolean verifyPasswordResetToken(VerifyPasswordResetTokenRequest request) {
        Optional<PasswordResetToken> token = passwordResetTokenRepository.findByTokenAndUserEmail(request.getToken(), request.getEmail());

        if(token.isPresent() && (token.get().getIsUsed() == false) && Instant.now().isBefore(token.get().getExpiresAt())){
                token.get().setIsUsed(true);
                passwordResetTokenRepository.save(token.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(PasswordReset request) throws MessagingException {

        User user = userRepository.findByEmail(request.getEmail());

        if(passwordService.validatePassword(request.getNewPassword(),user.getHashedPassword() )){

            return false;
        }
        user.setHashedPassword(passwordService.hashPassword(request.getNewPassword()));
        userRepository.save(user);

        String htmlBody = emailService.loadEmailTemplate(PASSWORD_CHANGE_CONFIRMATION_TEMPLATE, user);
        emailService.sendEmail(htmlBody, "Your Password Has Been Changed", user);

        return true;
    }

}
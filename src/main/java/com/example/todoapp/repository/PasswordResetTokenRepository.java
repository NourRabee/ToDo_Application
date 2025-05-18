package com.example.todoapp.repository;

import com.example.todoapp.domain.model.PasswordResetToken;
import com.example.todoapp.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer >{
    Optional<PasswordResetToken> findByTokenAndUserEmail(String token, String email);

    PasswordResetToken findTopByUserOrderByCreatedAtDesc(User user);
}

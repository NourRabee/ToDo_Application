package com.example.todoapp.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user; // In JPA (Java Persistence API), relationships between entities are handled through object references,
    // not raw foreign key IDs.

    @Column(nullable = false, unique = true)
    private String token;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime updatedAt;

    private Boolean isUsed;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusMinutes(10);
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}

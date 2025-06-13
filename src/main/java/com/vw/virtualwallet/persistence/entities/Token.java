package com.vw.virtualwallet.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a token in the application.
 * Tokens are used for authentication and authorization purposes.
 * Each token is associated with a user and has a validity period.
 *
 * @author caito
 */
@Entity
@Table(name = "tokens")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Token {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String token;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validateAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp user;
}

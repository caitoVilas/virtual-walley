package com.vw.virtualwallet.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a bank account in the Virtual Wallet application.
 * This class maps to the "accounts" table in the database.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "accounts")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 40)
    private String accountNumber;
    @Column(nullable = false, unique = true, length = 40)
    private String alias;
    private BigDecimal balance = BigDecimal.ZERO;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp user;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

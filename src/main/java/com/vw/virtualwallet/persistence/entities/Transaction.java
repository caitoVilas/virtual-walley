package com.vw.virtualwallet.persistence.entities;

import com.vw.virtualwallet.utils.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a transaction in the Virtual Wallet application.
 * This class maps to the "transactions" table in the database.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "transactions")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

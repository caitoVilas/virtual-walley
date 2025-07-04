package com.vw.virtualwallet.persistence.repositories;

import com.vw.virtualwallet.persistence.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Account entities.
 * Provides methods to perform CRUD operations on accounts in the Virtual Wallet application.
 *
 * @author caito
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumberOrAliasEqualsIgnoreCase(String accountNumber, String alias);
    Optional<Account> findByUserId(UUID userId);
}

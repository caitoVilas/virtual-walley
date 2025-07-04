package com.vw.virtualwallet.persistence.repositories;

import com.vw.virtualwallet.persistence.entities.Account;
import com.vw.virtualwallet.persistence.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Transaction entities.
 * This interface extends JpaRepository to provide CRUD operations for Transaction entities.
 *
 * @author caito
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByAccount(Account account, Pageable pageable);
}

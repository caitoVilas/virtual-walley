package com.vw.virtualwallet.persistence.repositories;

import com.vw.virtualwallet.persistence.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Token entities.
 * Provides methods to perform CRUD operations and custom queries.
 * Extends JpaRepository for basic CRUD functionality.
 *
 * @author caito
 */
public interface TokenRepository extends JpaRepository<Token, Long>{
    Optional<Token> findByToken(String token);
}

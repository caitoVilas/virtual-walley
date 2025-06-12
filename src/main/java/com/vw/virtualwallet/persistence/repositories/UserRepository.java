package com.vw.virtualwallet.persistence.repositories;

import com.vw.virtualwallet.persistence.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing UserApp entities.
 * Provides methods to perform CRUD operations on users in the Virtual Wallet application.
 *
 * @author caito
 *
 */
public interface UserRepository extends JpaRepository<UserApp, UUID> {
    Optional<UserApp> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByDniAndIdNot(String dni, UUID id);
    List<UserApp> findByNameContainingIgnoreCase(String name);
}

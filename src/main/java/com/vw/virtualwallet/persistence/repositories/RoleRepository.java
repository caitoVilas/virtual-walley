package com.vw.virtualwallet.persistence.repositories;

import com.vw.virtualwallet.persistence.entities.Role;
import com.vw.virtualwallet.utils.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 * Provides methods to perform CRUD operations on roles in the Virtual Wallet application.
 *
 * @author caito
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}

package com.vw.virtualwallet.persistence.entities;

import com.vw.virtualwallet.utils.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

/**
 * Entity representing a role in the Virtual Wallet application.
 * This class maps to the "roles" table in the database.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName name;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String getAuthority() {
        return name.name();
    }
}

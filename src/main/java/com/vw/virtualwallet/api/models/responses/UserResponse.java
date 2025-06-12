package com.vw.virtualwallet.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * UserResponse class represents the response model for user-related operations.
 * It contains user details such as ID, name, surname, address, DNI, telephone, and email.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserResponse implements Serializable {
    private UUID id;
    private String nane;
    private String surname;
    private String address;
    private String dni;
    private String telephone;
    private String email;
    private String accontNumber;
    private String alias;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal balance;
    private Set<RoleResponse> roles;
}

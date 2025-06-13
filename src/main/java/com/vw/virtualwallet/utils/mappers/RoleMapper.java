package com.vw.virtualwallet.utils.mappers;

import com.vw.virtualwallet.api.models.responses.RoleResponse;
import com.vw.virtualwallet.persistence.entities.Role;

/**
 * Mapper class to convert Role entity to RoleResponse DTO.
 *
 * @author caito
 *
 */
public class RoleMapper {

    /**
     * Converts a Role entity to a RoleResponse DTO.
     *
     * @param role the Role entity to convert
     * @return a RoleResponse DTO containing the role name
     */
    public static RoleResponse mapToDto(Role role) {
        return RoleResponse.builder()
                .role(role.getName())
                .build();
    }
}

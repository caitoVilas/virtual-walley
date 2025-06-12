package com.vw.virtualwallet.utils.mappers;

import com.vw.virtualwallet.api.models.responses.RoleResponse;
import com.vw.virtualwallet.persistence.entities.Role;

public class RoleMapper {

    public static RoleResponse mapToDto(Role role) {
        return RoleResponse.builder()
                .role(role.getName())
                .build();
    }
}

package com.vw.virtualwallet.api.models.responses;

import com.vw.virtualwallet.utils.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class RoleResponse implements Serializable {
    private RoleName role;
}

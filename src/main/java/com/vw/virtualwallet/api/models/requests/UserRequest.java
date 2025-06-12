package com.vw.virtualwallet.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserRequest implements Serializable {
    private String nane;
    private String surname;
    private String address;
    private String dni;
    private String telephone;
    private String email;
    private String password;
    private String confirmPassword;
}

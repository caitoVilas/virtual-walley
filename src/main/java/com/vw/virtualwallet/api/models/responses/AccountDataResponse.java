package com.vw.virtualwallet.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AccountDataResponse class represents the response model for account data in the Virtual Wallet application.
 * It contains various fields related to the user's account information.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class AccountDataResponse implements Serializable {
    private String accondNumber;
    private String alias;
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private String dni;
}

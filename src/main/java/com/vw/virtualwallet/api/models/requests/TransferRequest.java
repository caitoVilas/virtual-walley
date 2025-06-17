package com.vw.virtualwallet.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request model for transferring funds in the Virtual Wallet application.
 * This class encapsulates the data required for a transfer request.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class TransferRequest implements Serializable {
    private BigDecimal amount;
    private String description;
    private String destinationAccount;
}

package com.vw.virtualwallet.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request model for creating or updating a transaction in the Virtual Wallet application.
 * This class is used to encapsulate the data required for a transaction request.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class TransactionRequest implements Serializable {
    private BigDecimal amount;
    private String description;
}

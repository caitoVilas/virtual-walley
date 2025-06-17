package com.vw.virtualwallet.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CheckBallance class represents the response model for checking the balance in the Virtual Wallet application.
 * It contains the user's current balance formatted as a string with two decimal places.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class CheckBallanceResponse implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal balance;
}

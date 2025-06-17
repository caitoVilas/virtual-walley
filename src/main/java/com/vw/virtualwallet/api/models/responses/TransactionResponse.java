package com.vw.virtualwallet.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TransactionResponse class represents the response model for transaction-related operations.
 * It contains details about a transaction such as ID, type, amount, description, and date/time.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class TransactionResponse implements Serializable {
    private Long id;
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private String amount;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;
}

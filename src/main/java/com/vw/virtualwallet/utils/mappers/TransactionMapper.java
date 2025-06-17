package com.vw.virtualwallet.utils.mappers;

import com.vw.virtualwallet.api.models.responses.TransactionResponse;
import com.vw.virtualwallet.persistence.entities.Transaction;

/**
 * TransactionMapper class provides methods to map Transaction entity to TransactionResponse DTO.
 * It is used to convert data from the persistence layer to the API response format.
 *
 * @author caito
 *
 */
public class TransactionMapper {

    /**
     * Maps a Transaction entity to a TransactionResponse DTO.
     *
     * @param entity the Transaction entity to be mapped
     * @return a TransactionResponse DTO containing the mapped data
     */
    public static TransactionResponse mapToDto(Transaction entity) {
        return TransactionResponse.builder()
                .id(entity.getId())
                .type(entity.getType().name())
                .amount(entity.getAmount().toString())
                .description(entity.getDescription())
                .dateTime(entity.getDateTime())
                .build();
    }
}

package com.vw.virtualwallet.api.controllers.impl;

import com.vw.virtualwallet.api.controllers.contracts.TransactionController;
import com.vw.virtualwallet.api.models.requests.TransactionRequest;
import com.vw.virtualwallet.api.models.requests.TransferRequest;
import com.vw.virtualwallet.api.models.responses.TransactionResponse;
import com.vw.virtualwallet.services.contracts.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TransactionControllerImpl is the implementation of the TransactionController interface.
 * It handles transaction-related operations such as depositing and transferring money.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction API", description = "Transaction management operations")
public class TransactionControllerImpl  implements TransactionController {
    private final TransactionService transactionService;

    @Override
    public ResponseEntity<?> deposit(TransactionRequest request, Authentication authentication) {
        transactionService.deposit(request, authentication);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> transfer(TransferRequest request, Authentication authentication) {
        transactionService.trasnfer(request, authentication);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Page<TransactionResponse>> getTransactions(Authentication authentication, int page, int size) {
        return ResponseEntity.ok(transactionService.getTransactions(authentication, page, size));
    }
}

package com.vw.virtualwallet.api.controllers.contracts;

import com.vw.virtualwallet.api.models.requests.TransactionRequest;
import com.vw.virtualwallet.api.models.requests.TransferRequest;
import com.vw.virtualwallet.api.models.responses.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * TransctionContronller interface defines the contract for transaction-related operations.
 * It provides an endpoint to deposit money into the user's account.
 *
 * @author caito
 *
 */
public interface TransactionController {

    @PostMapping("/deposit")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Deposit money into the user's account")
    @Parameter(name = "request", description = "Transaction request containing amount and description")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Deposit successful"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation failed"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deposit(@RequestBody TransactionRequest request, Authentication authentication);

    @PostMapping("/transfer")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Transfer money between accounts")
    @Parameter(name = "request", description = "Transaction request containing amount, description, and target ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transfer successful"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation failed"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request, Authentication authentication);

    @GetMapping("/transactions")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get transactions for the authenticated user")
    @Parameters({
        @Parameter(name = "page", description = "Page number for pagination", required = false, example = "0"),
        @Parameter(name = "size", description = "Page size for pagination", required = false, example = "10")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
                      Authentication authentication,
                      @Parameter(description = "Page number for pagination", required = false, example = "0") int page,
                      @Parameter(description = "Page size for pagination", required = false, example = "10") int size
    );
}

package com.vw.virtualwallet.api.controllers.contracts;

import com.vw.virtualwallet.api.models.responses.AccountDataResponse;
import com.vw.virtualwallet.api.models.responses.CheckBallanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import javax.lang.model.element.Name;

/**
 * AccountController interface defines the contract for account-related operations.
 * It provides endpoints to check balance, retrieve account data, and search for accounts.
 *
 * @author caito
 *
 */
public interface AccountController {

    @GetMapping("/balance")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Check the balance of the user's account")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CheckBallanceResponse> checkBalance(Authentication authentication) throws Exception;

    @GetMapping("/account-data")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Retrieve account data for the authenticated user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account data retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountDataResponse> getData(Authentication authentication);

    @GetMapping("/search-account")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Search for an account by account number or alias")
    @Parameter(name ="search", description = "Account number or alias to search for")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account found successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountDataResponse> getByAccountOrAlias(String search);
}

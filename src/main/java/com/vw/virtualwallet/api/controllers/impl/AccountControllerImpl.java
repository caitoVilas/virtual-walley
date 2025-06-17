package com.vw.virtualwallet.api.controllers.impl;

import com.vw.virtualwallet.api.controllers.contracts.AccountController;
import com.vw.virtualwallet.api.models.responses.AccountDataResponse;
import com.vw.virtualwallet.api.models.responses.CheckBallanceResponse;
import com.vw.virtualwallet.services.contracts.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountControllerImpl is the implementation of the AccountController interface.
 * It provides endpoints for account management operations such as checking balance and retrieving account data.
 * This class is annotated with @RestController to indicate that it handles HTTP requests.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Account management operations")
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    @Override
    public ResponseEntity<CheckBallanceResponse> checkBalance(Authentication authentication) throws Exception {
        return ResponseEntity.ok(accountService.checkBalance(authentication));
    }

    @Override
    public ResponseEntity<AccountDataResponse> getData(Authentication authentication)  {
        return ResponseEntity.ok(accountService.getData(authentication));
    }

    @Override
    public ResponseEntity<AccountDataResponse> getByAccountOrAlias(String search) {
        return ResponseEntity.ok(accountService.getByAccountOrAlias(search));
    }
}
